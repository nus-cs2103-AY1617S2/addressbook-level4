/**
 * OffDoc - portable offline documentation
 * Copyright (c) 2015 M. A. Rizvi (MIT Licensed)
 * https://github.com/EntitySK/offdoc
 */
var mdfile = "README.md"; // Default File
var navbar = "NAV.md"; // Nav Bar

// Get parameters from URL
var GET = {};
var query = window.location.search.substring(1).split("&");
for (var i = 0, max = query.length; i < max; i++)
{
  if (query[i] === "")
    continue;
  var param = query[i].split("=");
  GET[decodeURIComponent(param[0])] = decodeURIComponent(param[1] || "");
}
if(GET.mdfile)
{
  mdfile = GET.mdfile;
}

// Add title
document.getElementById("title").innerHTML = "[OffDoc]: " + mdfile;

// Read markdown file into page
$.get(mdfile, function(bodydata) {
  $.get(navbar, function(navdata) {
    document.getElementById("body").innerHTML = bodydata + "\n\n---\n\n### [Navigation]:\n" + navdata;
    $.getScript(".offdoc/strapdown.js", function(){}); // Execute strapdown.js
  }, 'text');
}, 'text');
