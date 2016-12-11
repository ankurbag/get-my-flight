<html>
<head>
	<title>API Example</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script type="text/javascript">

		var accessToken = "b206ef8a0f044926b0e98cca54316b8b";
		var baseUrl = "https://api.api.ai/v1/";

		$(document).ready(function() {
			$("#input").keypress(function(event) {
				if (event.which == 13) {
					event.preventDefault();
					send();
				}
			});
			$("#rec").click(function(event) {
				switchRecognition();
			});
		});

		var recognition;

		function startRecognition() {
			recognition = new webkitSpeechRecognition();
			recognition.onstart = function(event) {
				updateRec();
			};
			recognition.onresult = function(event) {
				var text = "";
			    for (var i = event.resultIndex; i < event.results.length; ++i) {
			    	text += event.results[i][0].transcript;
			    }
			    setInput(text);
				stopRecognition();
			};
			recognition.onend = function() {
				stopRecognition();
			};
			recognition.lang = "en-US";
			recognition.start();
		}
	
		function stopRecognition() {
			if (recognition) {
				recognition.stop();
				recognition = null;
			}
			updateRec();
		}

		function switchRecognition() {
			if (recognition) {
				stopRecognition();
			} else {
				startRecognition();
			}
		}

		function setInput(text) {
			$("#input").val(text);
			send();
		}

		function updateRec() {
			$("#rec").text(recognition ? "Stop" : "Speak");
		}

		function send() {
			var text = $("#input").val();
			$.ajax({
				type: "POST",
				url: baseUrl + "query?v=20150910",
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				headers: {
					"Authorization": "Bearer " + accessToken
				},
				data: JSON.stringify({ query: text, lang: "en", sessionId: "somerandomthing" }),

				success: function(data) {
				//setResponse(JSON.stringify(data, undefined, 2));
					var jsonobj = JSON.stringify(data, undefined, 2);
					var jobj = JSON.parse(jsonobj.toString())
					console.log(jobj);
					setResponse("BOT>" +jobj.result.fulfillment.speech);
					if(jobj.result.fulfillment.data !=null){
						setResponse("BOT> Carrier :" +jobj.result.fulfillment.data.carrier+" is available at USD " + jobj.result.fulfillment.data.actualPrice + " It is predicted to go down to USD " + jobj.result.fulfillment.data.predictedPrice);
					}
					else if(jobj.result.fulfillment.data ==null && (jobj.result.resolvedQuery).toLowerCase()!="hi"){
						setResponse("BOT> It seems there is no fall predicted. SO try booking ASAP");
					}
				},
				error: function() {
					setResponse("Internal Server Error");
				}
			});
			setResponse("Book ASAP prices wont go down");
		}

		function setResponse(val) {
			$("#response").text(val);
		}

	</script>
	<style type="text/css">
		//body { width: 500px; margin: 0 auto; text-align: center; margin-top: 20px; }
		//div {  position: absolute; }
		//input { width: 400px; }
		//button { width: 50px; }
		textarea { width: 100%; }
	</style>
</head>
<body>
	<!--<nav class="navbar navbar-inverse">
	  <ul class="nav navbar-nav">
		<li><a href="#"></a></li>
		<li><a href="#"></a></li>
	  </ul>
	  <p class="navbar-text">Some text</p>
	</nav>!-->
	<div class="container">
  <h1>GetMyFlightBot<img src="Chat-icon.png"></img></h1>
  <div class="panel-group">
    <div class="panel panel-primary">
      <div class="panel-heading"></div>
      <div class="panel-body">
	  
		<div>
		
		<div class="form-group has-success has-feedback">
		  <label class="control-label sr-only" for="input">Input group with success</label>
		  <div class="input-group">
			<span class="input-group-addon">ENTER QUERY</span>
			<input type="text" class="form-control" id="input" aria-describedby="inputGroupSuccess4Status">
		  </div>
		  <span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
		  <span id="inputGroupSuccess4Status" class="sr-only">(success)</span>
		</div>
				<textarea id="response" cols="40" rows="20"></textarea>
		</div>
	  </div>
    </div>

    
  </div>
</div>
	
</body>
</html>