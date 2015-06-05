/* this *is* the console in this javaservlet architecture
 * see javascript console for exceptions eg json parse errors
*/

$(document).ready(function() {

  function handle(data, status) {
    console.log(data);
    $('#screen').html($('#screen').html() + data.screen); // app sends print or println equivalents
    window.scrollTo(0, document.documentElement.scrollHeight); // keep bottom in view
    switch (data.method) {
      case "read":
        $(document).keypress(function(event){
            //alert(event.which);
            $(document).off("keypress");
            var char = String.fromCharCode(event.which);
            if (event.which == 13) char = '.'; // control char in return json is not allowed
            $('#screen').html($('#screen').html() + char);
            post(char);
        });
        break;
      case "readln":
        var text = "";
        $(document).keypress(function(event){
            //alert(event.which);
            if (event.which == 13) {
              // end of line
              $(document).off("keypress");
              post(text);
            } else {
              var char = String.fromCharCode(event.which);
              $('#screen').html($('#screen').html() + char);
              text += char;
            }
        });
        break;
    }
  }

  function post(input) {
    $.post('/game2', {input:input}, function(data, status) {handle(data,status);}, "json");
  }
  
  $.get('/monster', function(data, status) {handle(data,status);}, "json");
  
});
