package org.teamstbf.yats.model.item;

public class Schedule {

		private Timing startTime;
		private Timing endTime;
		private Deadline deadline;
		
		/**
		 * Represents an Event schedule in TaskManager.
		 * Values cannot be null
		 * @param startTime
		 * @param endTime
		 * @param deadline
		 */
		public Schedule(Timing startTime, Timing endTime, Deadline deadline) {
			assert startTime != null;
			assert endTime != null;
			assert deadline != null;
			this.startTime = startTime;
			this.endTime = endTime;
			this.deadline = deadline;
		}
		
		public Schedule() {
			
		}
		
		public Timing getStartTime() {
			return startTime;
		}
		
		public Timing getEndTime() {
			return endTime;
		}
		
		public Deadline getDeadline() {
			return deadline;
		}
		
		public void setStarTime(Timing startTime) {
			this.startTime = startTime;
		}
		
		public void setEndTime(Timing endTime) {
			this.endTime = endTime;
		}
		
		public void setDeadline(Deadline deadline) {
			this.deadline = deadline;
		}

}
