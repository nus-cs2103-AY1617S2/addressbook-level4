#include <stdio.h>
#include <string.h>
#include "LPC17xx.h"
#include "lpc17xx_timer.h"
#include "lpc17xx_gpio.h"
#include "lpc17xx_pinsel.h"
#include "lpc17xx_i2c.h"
#include "lpc17xx_ssp.h"
#include "lpc17xx_uart.h"

#include "acc.h"                //accelerometer
#include "pca9532.h"    //arrays of leds - port expander
#include "light.h"              //light sensor
#include "rgb.h"                //RGB LED
#include "temp.h"               //temperature sensor
#include "led7seg.h"    //7-segment
#include "oled.h"               //LED Display
#include "rotary.h"             //rotary
#include "joystick.h"   //joystick

#define N_SAMPLE 10
#define ACC_ST_BRIGHT 50
#define LSTS_ST_BRIGHT 1000
#define ACC_ST_DIM 100
#define LSTS_ST_DIM 3000
#define BRIGHTNESS_CONDITION 800
#define LS_MAX 972
#define LS_MIN 0

#define REPORTING_TIME 5000
#define DISTRESS_TIME 1000
volatile int RT=REPORTING_TIME; //variable to set UART reporting time
volatile char RT_DISPLAY[2]="5";

//variables used to change temperature threshold setting
volatile uint32_t TEMP_WARN=260;
volatile int TW_RT_update = 0;
volatile char TEMP_WARN_DISPLAY[5]="26.0";
volatile int LOUDNESS=3;
/*   variables used for reading temperature    */
volatile uint32_t Tout_counter=0;
uint32_t Tout_timer;

typedef enum{
                REGULAR,
                RELAY
} MODE;
MODE CURRENT_MODE=REGULAR;

typedef enum{
                DIM,
                BRIGHT
} BRIGHTNESS;
BRIGHTNESS CURRENT_BRIGHTNESS=BRIGHT;

volatile uint32_t msTicks;

/*---------->>flags are used to signal when parameters are to be updated<<--------------*/
volatile uint8_t ST_flag=0, THRESHOLD_flag=0, LIGHTINTERRUPT_UPDATE_flag=0;
volatile uint32_t OLEDUPDATE_flag=0;

/*---------->>timer values are used to estimate when to set flags<<----------*/
uint32_t SEG_timer, RGB_timer, ACC_timer, LSTS_timer, REPORT_timer;

/*---------->>sampling timing variables to change easily in code<<----------*/
uint32_t ACC_samplingtime=ACC_ST_BRIGHT, LSTS_samplingtime=LSTS_ST_BRIGHT;

uint8_t SEG_value, ALARM_status;
int CURRENT_TEMP=0, CURRENT_LUX=0, CURRENT_ZVAR=0;
int8_t z_mean;
volatile char DATAREPORT[43]={'\0'}, DATA_received[23]={'\0'}, DATA_validated[23]={'\0'};
char lux_string[4]={'\0'}, temp_string[5]={'\0'}, acc_string[4]={'\0'};
volatile int PCA_DUTY=99; //LIGHT ARRAY DUTY CYCLE

//>>>GENERIC FLAG GENERATOR FUNCTION FOR RGB, 7-SEG AND THE SENSOR SAMPLING TIMERS
uint8_t flag_check(uint32_t *timer, uint32_t samplingperiod)  //>>>CONVERT ALL THE 4 FLAGS INTO 1 FUNCTION
{
        int flag=0;

        if((msTicks - *timer) >= samplingperiod)
        {
                flag = 1;
                *timer = msTicks;
        }
        return flag;
}

void ALARM_update(void)
{
        if(CURRENT_TEMP >= TEMP_WARN)
                {
                        ALARM_status = 1;
                        LPC_TIM0->IR = 0xFFFFFFFF; //clear timer interrupt. IR is Interrupt Register
                        NVIC_EnableIRQ(TIMER0_IRQn);
                        LPC_TIM0->TCR |= 0x1; //enable Timer Counter to start counting
                }
        if(CURRENT_MODE == RELAY)
                ALARM_status = 0;
}

void SysTick_Handler(void)
{
        msTicks++;

        return;
}

void check_SW4(void)            //----polling for SW4 to off the alarm
{
        if( !((GPIO_ReadValue(1) >> 31) & 0x01) )
                ALARM_status=0;
        if(ALARM_status == 0)
        {
                LPC_TIM0->IR = 0xFFFFFFFF; //clear timer interrupt. IR is Interrupt Register
                NVIC_DisableIRQ(TIMER0_IRQn);
                LPC_TIM0->TCR &= ~0x1; //stops Timer Counter from counting
                LPC_TIM0->TCR |= 1<<1; //reset counter
                LPC_TIM0->TCR &= ~(1<<1); //stop resetting counter
        }
        return;
}

/*  updates the oled screen with latest data
        0x00001 for LSTS updates
        0x00010 for ACC updates
        0x00100 for MODE updates
        0x01000 for B.Cond updates
        0X10000 for Temp.Warn updates           */
void oled_updatedata(void)
{
        char THRESH[15]="TW:", RTdisplay[5]="RT:";
        if(OLEDUPDATE_flag  & 0x10000)
        {
                strcat(RTdisplay, RT_DISPLAY);
                strcat(THRESH, TEMP_WARN_DISPLAY);
                if(TW_RT_update==1)
                        oled_putString(1, 55, THRESH, OLED_COLOR_BLACK, OLED_COLOR_WHITE);
                else
                {       oled_putString(1, 55, THRESH, OLED_COLOR_WHITE, OLED_COLOR_BLACK);
                        TEMP_WARN = (TEMP_WARN_DISPLAY[0]-'0')*100 + (TEMP_WARN_DISPLAY[1]-'0')*10 + (TEMP_WARN_DISPLAY[3]-'0');
                        if(TW_RT_update==2)
                                oled_putString(50, 55, RTdisplay, OLED_COLOR_BLACK, OLED_COLOR_WHITE);
                        else
                                oled_putString(50, 55, RTdisplay, OLED_COLOR_WHITE, OLED_COLOR_BLACK);
                }
                OLEDUPDATE_flag &= 0x01111;
        }
        if(OLEDUPDATE_flag & 0x0001) //LSTS
        {
                if(CURRENT_LUX >= 100)
                        snprintf(lux_string, 4, "%d", CURRENT_LUX);
                else if(CURRENT_LUX >= 10)
                        snprintf(lux_string, 4, " %d", CURRENT_LUX);
                else
                        snprintf(lux_string, 4, "  %d", CURRENT_LUX);
                oled_putString(56, 0, &lux_string, OLED_COLOR_WHITE, OLED_COLOR_BLACK);

                sprintf(temp_string, "%d", CURRENT_TEMP);
                temp_string[3] = temp_string[2];
                temp_string[2] = '.';
                temp_string[4] = '\0';
                oled_putString(50, 10, &temp_string, OLED_COLOR_WHITE, OLED_COLOR_BLACK);
                OLEDUPDATE_flag &= 0x11110;
        }
        if(OLEDUPDATE_flag & 0x0010) // ACC
        {
                if(CURRENT_ZVAR >= 100)
                        snprintf(acc_string, 4, "%d", CURRENT_ZVAR);
                else if(CURRENT_ZVAR >= 10)
                        snprintf(acc_string, 4, " %d", CURRENT_ZVAR);
                else
                        snprintf(acc_string, 4, "  %d", CURRENT_ZVAR);

                oled_putString(56, 20, &acc_string, OLED_COLOR_WHITE, OLED_COLOR_BLACK);
                OLEDUPDATE_flag &= 0x11101;
        }
        if(OLEDUPDATE_flag & 0x0100) // MODE
        {
                if(CURRENT_MODE == REGULAR)
                        oled_putString(50, 35, "REGULAR", OLED_COLOR_WHITE, OLED_COLOR_BLACK);
                else
                        oled_putString(50, 35, "RELAY  ", OLED_COLOR_WHITE, OLED_COLOR_BLACK);
                OLEDUPDATE_flag &= 0x11011;
        }
        if(OLEDUPDATE_flag & 0x1000) // B.Cond
        {
                if(CURRENT_MODE == RELAY)
                        oled_putString(50, 45, "N.A.  ", OLED_COLOR_WHITE, OLED_COLOR_BLACK);
                else if(CURRENT_BRIGHTNESS == BRIGHT)
                        oled_putString(50, 45, "BRIGHT", OLED_COLOR_WHITE, OLED_COLOR_BLACK);
                else
                        oled_putString(50, 45, "DIM   ", OLED_COLOR_WHITE, OLED_COLOR_BLACK);
                OLEDUPDATE_flag &= 0x10111;
        }
}

uint8_t acc_data[N_SAMPLE]={0};
uint8_t CBptr=0, CBcnt=0;
uint8_t z_variance(int z, int z_mean, int reset)        //c-version function to calculate variance
{
        int sum=0, j, diff;

        if(z_mean >= z)
                diff = z_mean - z;
        else
                diff = z - z_mean;
        acc_data[CBptr] = diff * diff;
        if(CBptr < (N_SAMPLE-1))
                CBptr++;
        else
                CBptr=0;
        if(CBcnt < (N_SAMPLE-1))
        {
                CBcnt++;
        }
        for(j=0; j<=CBcnt; j++)
        {
                sum = sum + acc_data[j];
        }
        return (sum/CBcnt);
}

void ACC_sample(void)   //the main function to read ACC values
{
        int8_t x, y, z;

        if(flag_check(&ACC_timer, ACC_samplingtime))
        {
                acc_read(&x, &y, &z);
                CURRENT_ZVAR = z_variance(z, z_mean, 0);
                OLEDUPDATE_flag |= 0x0010;
        }
        return;
}

int temperature_read(void)   //customized function to read the current temperature
{
        int periodcount, period;

        periodcount = Tout_counter;
        period = ((msTicks - Tout_timer)*1000) / periodcount;

        Tout_counter = 0;
        Tout_timer = msTicks;
        return (period - 2731);
}

void LSTS_sample(void)   //the main function to read LS and TS values
{
        if(flag_check(&LSTS_timer, LSTS_samplingtime))
        {
                CURRENT_LUX = light_read();
                CURRENT_TEMP = temperature_read();
                OLEDUPDATE_flag |= 0x0001;
        }
        return;
}

void THRESHOLD_update(void) //updates the threshold for the light sensor
{
        if(THRESHOLD_flag)
        {
                if(CURRENT_BRIGHTNESS==BRIGHT)
                {
                        light_setHiThreshold(LS_MAX);
                        light_setLoThreshold(BRIGHTNESS_CONDITION);
                }
                else
                {
                        light_setHiThreshold(BRIGHTNESS_CONDITION);
                        light_setLoThreshold(LS_MIN);
                }
        THRESHOLD_flag = 0;
        }
        return;
}

void ST_update(void) //updates all the sampling time for LS, TS, ACC
{
        if(ST_flag)
        {
                if(CURRENT_MODE==RELAY)
                {
                        ACC_samplingtime = ACC_ST_DIM;
                        LSTS_samplingtime = LSTS_ST_DIM;
                }
                else if(CURRENT_BRIGHTNESS==BRIGHT)
                {
                        ACC_samplingtime = ACC_ST_BRIGHT;
                        LSTS_samplingtime = LSTS_ST_BRIGHT;
                }
                else if(CURRENT_BRIGHTNESS==DIM)
                {
                        ACC_samplingtime = ACC_ST_DIM;
                        LSTS_samplingtime = LSTS_ST_DIM;
                }
        ST_flag = 0;
        }
        return;
}

void LIGHTINTERRUPT_update(void) //disable/enable the light sensor interrupt when mode is toggled
{
        if(LIGHTINTERRUPT_UPDATE_flag)
        {
                if(CURRENT_MODE==RELAY)
                        LPC_GPIOINT->IO2IntEnF &= 0xFFDF;  //disables gpio interrupt from light sensor
                else
                {
                        light_clearIrqStatus();
                        LPC_GPIOINT->IO2IntClr = 1<<5;
                        LPC_GPIOINT->IO2IntEnF |= 1<<5; //re-enable light sensor gpio interrupt
                }
        }
}

void EINT0_IRQHandler(void)
{
        CURRENT_MODE = (~CURRENT_MODE) & (0x01);

        ST_flag = 1;
        THRESHOLD_flag = 1;
        LIGHTINTERRUPT_UPDATE_flag = 1;
        OLEDUPDATE_flag |= 0x1100;
        LPC_SC->EXTINT = 1;

        return;
}

void EINT3_IRQHandler(void)
{
        int rotary_dir, joystick_dir;

        //      joystick
        if (((LPC_GPIOINT->IO2IntStatF>>3)&0x1) | ((LPC_GPIOINT->IO2IntStatF>>4)&0x1) |
            ((LPC_GPIOINT->IO0IntStatF>>15)&0x1) | ((LPC_GPIOINT->IO0IntStatF>>16)&0x1) |
                ((LPC_GPIOINT->IO0IntStatF>>17)&0x1))
        {
                joystick_dir = joystick_read();
                if(joystick_dir == 0x01)
                {       TW_RT_update = TW_RT_update + 1;
                        if(TW_RT_update == 3)
                                TW_RT_update = 0;
                }
                if(joystick_dir == 0x02 && TW_RT_update==1)
                {       if((TEMP_WARN_DISPLAY[1]-'0')<9)
                                TEMP_WARN_DISPLAY[1] += 1;
                        else if(TEMP_WARN_DISPLAY[1]=='9')
                        {       TEMP_WARN_DISPLAY[0] += 1;
                                TEMP_WARN_DISPLAY[1] = '0';             }
                }
                if(joystick_dir == 0x04 && TW_RT_update==1)
                {       if((TEMP_WARN_DISPLAY[1]-'0')>0)
                                TEMP_WARN_DISPLAY[1] -= 1;
                        else if(TEMP_WARN_DISPLAY[1]=='0')
                        {       TEMP_WARN_DISPLAY[0] -= 1;
                                TEMP_WARN_DISPLAY[1] = '9';             }
                }
                if(joystick_dir == 0x08 && TW_RT_update==1)
                {       if((TEMP_WARN_DISPLAY[3]-'0')>0)
                                TEMP_WARN_DISPLAY[3] -= 1;              }
                if(joystick_dir == 0x10 && TW_RT_update==1)
                {       if((TEMP_WARN_DISPLAY[3]-'0')<9)
                                TEMP_WARN_DISPLAY[3] += 1;              }
                if(joystick_dir == 0x02 && TW_RT_update==2)
                {
                        if(RT==REPORTING_TIME)
                        {       RT_DISPLAY[0] = '1';
                                RT=DISTRESS_TIME;               }
                        else
                        {       RT_DISPLAY[0] = '5';
                                RT=REPORTING_TIME;              }
                }
                OLEDUPDATE_flag = 0x10000;
                LPC_GPIOINT->IO0IntClr = 1<<15;
                LPC_GPIOINT->IO0IntClr = 1<<16;
                LPC_GPIOINT->IO0IntClr = 1<<17;
                LPC_GPIOINT->IO2IntClr = 1<❤;
                LPC_GPIOINT->IO2IntClr = 1<<4;
        }

        //SW5 rotary interrupt
        if (((LPC_GPIOINT->IO0IntStatF>>24)&0x1) | ((LPC_GPIOINT->IO0IntStatF>>25)&0x1)) //light sensor interrupt
        {
                rotary_dir = rotary_read();
                if(rotary_dir == 2)
                {
                        if(PCA_DUTY == 0)
                                PCA_DUTY = 1;
                        else if(PCA_DUTY < 50)
                                PCA_DUTY = PCA_DUTY * 2;
                        else
                                PCA_DUTY = 99;
                        if(LOUDNESS<10)
                                LOUDNESS++;
                }
                if(rotary_dir == 1)
                {
                        if(PCA_DUTY > 64)
                                PCA_DUTY = 64;
                        else if(PCA_DUTY > 1)
                                PCA_DUTY = PCA_DUTY / 2;
                        else
                                PCA_DUTY = 0;
                        if(LOUDNESS>0)
                                LOUDNESS--;
                }
                LPC_TIM0->MR0 = LOUDNESS;
                LPC_GPIOINT->IO0IntClr = 1<<24;
                LPC_GPIOINT->IO0IntClr = 1<<25;
        }

        if ((LPC_GPIOINT->IO2IntStatF>>5)& 0x1) //light sensor interrupt
        {
                CURRENT_BRIGHTNESS = (~CURRENT_BRIGHTNESS) & (0x01);
                ST_flag = 1;
                THRESHOLD_flag = 1;

                LPC_GPIOINT->IO2IntClr = 1<<5;
                OLEDUPDATE_flag |= 0x1000;
        }

        if ((LPC_GPIOINT->IO0IntStatF>>2)& 0x1) //temp sensor interrupt
        {
                Tout_counter++;
                LPC_GPIOINT->IO0IntClr = 1<<2;
        }

        return;
}

void SEGMENT_DISPLAY(void)
{
        if(flag_check(&SEG_timer, 1000))
        {
                if(SEG_value < 9)
                {
                        SEG_value++;
                }
                else
                {
                        SEG_value = 0;
                }
                led7seg_setChar(('0'+SEG_value), FALSE);
        }
        return;
}

void INDICATOR_REGULAR(void)
{
        GPIO_ClearValue( 2, 1);   //OFF THE RGB
        pca9532_setBlink0Duty(PCA_DUTY);
        pca9532_setBlink0Leds(0xFF00);
}

//switch the RGB on-off
static void RGB_switch()
{
        if(GPIO_ReadValue(2) & 1)
        {
                rgb_setLeds(0x00);
        }
        else
        {
                rgb_setLeds(0x01);
        }
        return;
}

void INDICATOR_RELAY(void)
{
        pca9532_setLeds(0, 0xFF00);  //OFF THE LED-ARRAY
        if(flag_check(&RGB_timer, 2000))
                RGB_switch();
        return;
}

//initialize I2C2
void init_i2c2(void)
{
        PINSEL_CFG_Type PinCfg;

        // Initialize I2C2 pin connect
        PinCfg.Portnum = 0;
        PinCfg.Pinnum = 10;
        PinCfg.Funcnum = 2;
        PINSEL_ConfigPin(&PinCfg);

        PinCfg.Pinnum = 11;
        PINSEL_ConfigPin(&PinCfg);

        // Initialize I2C peripheral
        I2C_Init(LPC_I2C2, 100000);

        // Enable I2C2 operation
        I2C_Cmd(LPC_I2C2, ENABLE);
}
//initialize GPIO
void init_gpio(void)
{
        PINSEL_CFG_Type PinCfg;

        //SW3
        PinCfg.Portnum = 2;
        PinCfg.Pinnum = 10;
        PinCfg.Funcnum = 1;
        PinCfg.Pinmode = 0;
        PinCfg.OpenDrain = 0;
        PINSEL_ConfigPin(&PinCfg);
        GPIO_SetDir(2, 1<<10, 0);

        //SW4
        PinCfg.Portnum = 1;
        PinCfg.Pinnum = 31;
        PinCfg.Funcnum = 0;
        PINSEL_ConfigPin(&PinCfg);
        GPIO_SetDir(1, 1<❤1, 0);

        //Speaker
        GPIO_SetDir(0, 1<<26, 1);
        GPIO_SetDir(0, 1<<27, 1); //LM4811-clk
        GPIO_SetDir(0, 1<<28, 1); //LM4811-up/dn
        GPIO_SetDir(2, 1<<13, 1); //LM4811-shutdn
        GPIO_ClearValue(0, 1<<27); //LM4811-clk
        GPIO_ClearValue(0, 1<<28); //LM4811-up/dn
        GPIO_ClearValue(2, 1<<13); //LM4811-shutdn

        //Light Interrupt
        PinCfg.Portnum = 2;
        PinCfg.Pinnum = 5;
        PinCfg.Funcnum = 0;
        PINSEL_ConfigPin(&PinCfg);
        GPIO_SetDir(2, 1<<5, 0);

        //Temperature Sensor
        PinCfg.Portnum = 0;
        PinCfg.Pinnum = 2;
        PinCfg.Funcnum = 0;
        PINSEL_ConfigPin(&PinCfg);
        GPIO_SetDir(0, 1<<2, 0);
        return;
}
//initialize SSP
void init_ssp(void)
{
        SSP_CFG_Type SSP_ConfigStruct;
        PINSEL_CFG_Type PinCfg;

        /*
         * Initialize SPI pin connect
         * P0.7 - SCK;
         * P0.8 - MISO
         * P0.9 - MOSI
         * P2.2 - SSEL - used as GPIO
         */
        PinCfg.Funcnum = 2;
        PinCfg.OpenDrain = 0;
        PinCfg.Pinmode = 0;
        PinCfg.Portnum = 0;
        PinCfg.Pinnum = 7;
        PINSEL_ConfigPin(&PinCfg);
        PinCfg.Pinnum = 8;
        PINSEL_ConfigPin(&PinCfg);
        PinCfg.Pinnum = 9;
        PINSEL_ConfigPin(&PinCfg);
        PinCfg.Funcnum = 0;
        PinCfg.Portnum = 2;
        PinCfg.Pinnum = 2;
        PINSEL_ConfigPin(&PinCfg);

        SSP_ConfigStructInit(&SSP_ConfigStruct);

        // Initialize SSP peripheral with parameter given in structure above
        SSP_Init(LPC_SSP1, &SSP_ConfigStruct);

        // Enable SSP peripheral
        SSP_Cmd(LPC_SSP1, ENABLE);
        return;
}
//initialize UART
void pinsel_uart3(void)
{
        PINSEL_CFG_Type PinCfg;

        PinCfg.Portnum = 0;
        PinCfg.Pinnum = 0;
        PinCfg.Funcnum = 2;
        PINSEL_ConfigPin(&PinCfg);
        PinCfg.Pinnum = 1;
        PINSEL_ConfigPin(&PinCfg);
}
void init_uart(void)
{
        UART_CFG_Type uartCfg;

        uartCfg.Baud_rate = 115200;
        uartCfg.Databits = UART_DATABIT_8;
        uartCfg.Parity = UART_PARITY_NONE;
        uartCfg.Stopbits = UART_STOPBIT_1;
        pinsel_uart3();
        UART_Init(LPC_UART3, &uartCfg);
        UART_TxCmd(LPC_UART3, ENABLE);
}

void UART3_IRQHandler(void)
{
        uint8_t data = 0;
        uint32_t len = 0;
        int i;
        if(LPC_UART3->IIR & 0x4)
        {
                do
                {       UART_Receive(LPC_UART3, &data, 1, BLOCKING);
                        if (data != '\r')
                        {
                                len++;
                                DATA_received[len-1] = data;
                        }
                } while((len<22) && (data!='\r'));

                if(DATA_received[0] == '#' && DATA_received[21] =='#' && DATA_received[1]=='N' &&
                                DATA_received[5]=='_' && DATA_received[6]=='T' && DATA_received[11]=='_' &&
                                DATA_received[12]=='L' && DATA_received[16]=='_' && DATA_received[17]=='V')
                {
                        DATA_validated[0] = '_';
                        DATA_validated[21]= '\0';
                        for(i = 1; i<21; i++)
                                DATA_validated[i] = DATA_received[i];
                }
                else
                        DATA_validated[0] = '\0';
        }
}

void report_data(void)
{
        int i;
        if(flag_check(&REPORT_timer, RT))
        {
                snprintf(DATAREPORT, 43, "N075_T%s_L%s_V%s\0", temp_string, lux_string, acc_string);
                for(i = 0; i < 20; i++)
                {       if(DATAREPORT[i]==' ')
                                DATAREPORT[i] = '0';    }
                if(CURRENT_MODE == RELAY)
                {
                        strcat(DATAREPORT, DATA_validated);
                }
                strcat(DATAREPORT, "\r\0");

                UART_SendString(LPC_UART3, DATAREPORT);

                DATA_received[0]= '\0';
                DATA_received[21]= '\0';
                DATA_validated[0]= '\0';
        }
}

void TIMER_init(void)
{
        LPC_SC->PCONP |= 1<<1 & 0xEFEFF7DE;     //provide power to timer0
        LPC_SC->PCLKSEL0 |= (1<<2 | 1<❤);       //PCLK_TIMER0 set to CCLK(100MHz)/8
        LPC_TIM0->CTCR |= 00;                           //set to timer mode
        LPC_TIM0->TC = 0;                                       //initialize timer counter to 0
        LPC_TIM0->PC = 0;                                       //initialize prescale counter to 0
        LPC_TIM0->PR = 99;                              //pre-scale value set to divide clk freq by 10k
        LPC_TIM0->MR0 = LOUDNESS;                               //match register to further divide by 5 to give 250Hz
        LPC_TIM0->MR1 = 499;
        LPC_TIM0->MCR |= (0x1 | 3<❤); //enable reset(bit 1) and interrupt(bit 0) only, no stop

        return;
}

void TIMER0_IRQHandler()
{
        if(LPC_TIM0->IR & 0x01)                         //if MR0 triggered
                GPIO_ClearValue(0, 1<<26);
        else                                                            //if MR1 triggered
                GPIO_SetValue(0, 1<<26);

        LPC_TIM0->IR |= 0x03;                           //clear interrupt
}

void oled_initScreen(void)
{
        char LS[9]="LS     :", TS[9]="TS     :", ACC[9]="ACC    :",
                        MODE[16]="MODE   :REGULAR", BC[9]="B.Cond :", THRESH[15]="TW:", RTdisplay[5]="RT:";
        oled_clearScreen(OLED_COLOR_BLACK);
        oled_putString(1, 0, LS, OLED_COLOR_WHITE, OLED_COLOR_BLACK);
        oled_putString(1, 10, TS, OLED_COLOR_WHITE, OLED_COLOR_BLACK);
        oled_putString(1, 20, ACC, OLED_COLOR_WHITE, OLED_COLOR_BLACK);
        oled_line(1, 31, 90, 31, OLED_COLOR_WHITE);
        oled_putString(1, 35, MODE, OLED_COLOR_WHITE, OLED_COLOR_BLACK);
        oled_putString(1, 45, BC, OLED_COLOR_WHITE, OLED_COLOR_BLACK);
        oled_line(1, 53, 90, 53, OLED_COLOR_WHITE);
        strcat(THRESH, TEMP_WARN_DISPLAY);
        oled_putString(1, 55, THRESH, OLED_COLOR_WHITE, OLED_COLOR_BLACK);
        strcat(RTdisplay, RT_DISPLAY);
        oled_putString(50, 55, RTdisplay, OLED_COLOR_WHITE, OLED_COLOR_BLACK);
        return;
}

int main(void)
{
        int8_t x, y;
        ALARM_status = 0;
        SysTick_Config(SystemCoreClock/1000);
        SEG_timer=msTicks;
        RGB_timer=msTicks;
        ACC_timer=msTicks;
        LSTS_timer=msTicks;
        REPORT_timer=msTicks;

        init_i2c2();
        init_gpio();
        init_ssp();
        init_uart();
        TIMER_init();

        led7seg_init();
        SEG_value=0;
        led7seg_setChar(('0'+SEG_value), FALSE);

        rgb_init();
        rgb_setLeds(0x01);

        acc_init();
    acc_read(&x, &y, &z_mean);

    light_enable();
    light_clearIrqStatus();
    light_setHiThreshold(LS_MAX);
    light_setLoThreshold(BRIGHTNESS_CONDITION);
    pca9532_setBlink0Period(0);
    rotary_init();
    joystick_init();

    oled_init();
    oled_initScreen();

        //EINT3 Configuration
    LPC_SC->EXTINT = 1;
    //light sensor
    LPC_GPIOINT->IO2IntClr = 1<<5;
        LPC_GPIOINT->IO2IntEnF |= 1<<5;
        //temp sensor
        LPC_GPIOINT->IO0IntClr = 1<<2;
        LPC_GPIOINT->IO0IntEnF |= 1<<2;
        Tout_timer = msTicks;  //re-initialize Tout_timer to synchronize with tempsensor interrupt
        Tout_counter = 0;      //re-initialize Tout_counter to synchronize with tempsensor interrupt
        //rotary
        LPC_GPIOINT->IO0IntClr = 1<<24;
        LPC_GPIOINT->IO0IntEnF |= 1<<24;
        LPC_GPIOINT->IO0IntClr = 1<<25;
        LPC_GPIOINT->IO0IntEnF |= 1<<25;
        //joystick interrupt
        LPC_GPIOINT->IO0IntEnF |= 1<<15;
        LPC_GPIOINT->IO0IntEnF |= 1<<16;
        LPC_GPIOINT->IO0IntEnF |= 1<<17;
        LPC_GPIOINT->IO2IntEnF |= 1<❤;
        LPC_GPIOINT->IO2IntEnF |= 1<<4;
        NVIC_ClearPendingIRQ(EINT3_IRQn);
        NVIC_SetPriorityGrouping(4);
        NVIC_SetPriority(EINT3_IRQn, NVIC_EncodePriority(4,2,0));
        NVIC_EnableIRQ(EINT3_IRQn);

        //EINT0 Configuration
        LPC_SC->EXTMODE = 1;
        NVIC_ClearPendingIRQ(EINT0_IRQn);
        NVIC_SetPriorityGrouping(4);
        NVIC_SetPriority(EINT0_IRQn, NVIC_EncodePriority(4,0,0));
        NVIC_EnableIRQ(EINT0_IRQn);

        //UART interrupt Config
        UART_IntConfig(LPC_UART3, UART_INTCFG_RBR, ENABLE);
        NVIC_EnableIRQ(UART3_IRQn);

        while(1)
        {
                SEGMENT_DISPLAY();

                check_SW4();

                LIGHTINTERRUPT_update();
                THRESHOLD_update();
                ST_update();
                oled_updatedata();
                report_data();

                switch(CURRENT_MODE)
                {
                        case REGULAR:
                                INDICATOR_REGULAR();
                                ACC_sample();
                                LSTS_sample();
                                ALARM_update();
                                break;
                        case RELAY:
                                INDICATOR_RELAY();
                                ACC_sample();
                                LSTS_sample();
                                ALARM_update();
                                break;
                        default:
                                break;
                }
                light_clearIrqStatus();
        }
        return 0;
}