<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html><head>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
    <script src="<c:url value="/resources/websocket/sockjs-0.3.4.js" />" type="text/javascript" ></script>
    <script src="<c:url value="/resources/websocket/stomp.js" />" type="text/javascript" > </script>
  <script src="stomp.js"></script>
  <style>
      .box {
          width: 440px;
          float: left;
          margin: 0 20px 0 20px;
      }

      .box div, .box input {
          border: 1px solid;
          -moz-border-radius: 4px;
          border-radius: 4px;
          width: 100%;
          padding: 5px;
          margin: 3px 0 10px 0;
      }

      .box div {
          border-color: grey;
          height: 300px;
          overflow: auto;
      }

      div code {
          display: block;
      }

      #first div code {
          -moz-border-radius: 2px;
          border-radius: 2px;
          border: 1px solid #eee;
          margin-bottom: 5px;
      }

      #second div {
          font-size: 0.8em;
      }
  </style>
  <title>RabbitMQ Web STOMP Examples : Echo Server</title>
  <link href="main.css" rel="stylesheet" type="text/css"/>
</head><body lang="en">
    <h1><a href="index.html">RabbitMQ Web STOMP Examples</a> > Echo Server</h1>

    <div id="first" class="box">
      <h2>Received</h2>
      <div></div>
      <form><input autocomplete="off" value="Type here..."></input></form>
    </div>

    <div id="second" class="box">
      <h2>Logs</h2>
      <div></div>
    </div>

    <script>
        var has_had_focus = false;
        var pipe = function(el_name, send) {
            var div  = $(el_name + ' div');
            var inp  = $(el_name + ' input');
            var form = $(el_name + ' form');

            var print = function(m, p) {
                p = (p === undefined) ? '' : JSON.stringify(p);
                div.append($("<code>").text(m + ' ' + p));
                div.scrollTop(div.scrollTop() + 10000);
            };

            if (send) {
                form.submit(function() {
                    send(inp.val());
                    inp.val('');
                    return false;
                });
            }
            return print;
        };

      // Stomp.js boilerplate
      var ws = new SockJS('http://' + window.location.hostname + ':15674/stomp');
      var client = Stomp.over(ws);

      // SockJS does not support heart-beat: disable heart-beats
      client.heartbeat.outgoing = 0;
      client.heartbeat.incoming = 0;
      client.debug = pipe('#second');

      var print_first = pipe('#first', function(data) {
          client.send('/exchange/myExchange/test', {"content-type":"text/plain"}, data);
      });
      var on_connect = function(x) {
/*           id = client.subscribe("/exchange/myExchange/test", function(d) {
               print_first(d.body);
          }); */

          id = client.subscribe("/queue/myqueue", function(d) {
               print_first(d.body);
          }); 
      };
      var on_error =  function() {
        console.log('error');
      };
      client.connect('orlikeo', 'rabbit', on_connect, on_error, '/');

      $('#first input').focus(function() {
          if (!has_had_focus) {
              has_had_focus = true;
              $(this).val("");
          }
      });
    </script>
</body></html>
