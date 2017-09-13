var ws;
var selRoomId;
var connected = false;

function connect() {
	var socket = new WebSocket('wss://' + window.location.host + '/chatEndpoint');
	ws = Stomp.over(socket);

	ws.connect({}, function(frame) {
		ws.subscribe("/user/queue/errors", function(message) {
			BootstrapAlert.alert({
				  title: "Error",
				  message: message.body,
				  dissmissible: true,
				  autoHide: true,
				  hideTimeout: 3000
				});
		});		

		ws.subscribe("/topic/chatroom", function(message) {
			addChatRoom(JSON.parse(message.body));
		});
		
		ws.subscribe("/topic/message", function(message) {
			var chatMessage = JSON.parse(message.body);
			if (chatMessage.chatRoomId == selRoomId) {
				addMessage(chatMessage);
			} else {
				var el = $(document).find("#chatroom-count-"+chatMessage.chatRoomId);
				var count = parseInt(el.html())
				count = isNaN(count) ? 1 : count + 1;
				el.html(count);
			}
		});
		connected = true;
	}, function(error) {
		console.log("Error: " + error);
		connected = false;
		connect();
		console.log("Connected: " + connected);
//		BootstrapAlert.alert({
//			  title: "STOMP error",
//			  message: error,
//			  dissmissible: true,
//			  autoHide: false,
//			  hideTimeout: 1000
//			});
	});
}

function disconnect() {
	if (ws != null) {
		ws.close();
	}
}

function sendNewChatRoom() {
	var val = $("#newChatRoomName").val().trim();
	if (!val) return;
	$("#newChatRoomName").val("")
	if (!connected) {
		console.log("Connected False: " + connected);
		connect();
		console.log("Reconnect: " + connected);
	}
	var data = JSON.stringify({
		'content' : val
	})
	ws.send("/app/newchatroom", {}, data);
	$('#chatRoomNewModal').modal('hide')
}

function sendNewMessage() {
	var val = $("#newMessage").val().trim();
	if (!val) return;
	$("#newMessage").val("")
	if (!connected) {
		connect();
	}
	var data = JSON.stringify({
		'content' : val
	})
	ws.send("/app/newmessage/" + selRoomId, {}, data);
}

function esacapeHtml(text) {
	return jQuery('<div />').text(text).html();
}


function addChatRoom(chatRoom) {
	var elMsg = '<li class="left clearfix" data-id='+chatRoom.id+'>';
	elMsg += '<strong class="primary-font" id="chatroom-name-'+chatRoom.id+'">'+esacapeHtml(chatRoom.name)+'</strong>'; 
	elMsg += '<strong class="pull-right badge" id="chatroom-count-'+chatRoom.id+'"></strong>';
	elMsg += '</li>';
	$("#chatroom_list_ul").append(elMsg);
	$("#chatroom_list_div").scrollTop(function() {return this.scrollHeight; });
}

function addMessage(message,after = true) {
	var elMsg = '<li class="left clearfix" id="message-'+message.id+'">';
	elMsg += '<div class="clearfix">';
	elMsg += '<div class="clearfix">';
	elMsg += '<div class="pull-right">'+esacapeHtml(message.created)+'</div>';
	elMsg += '<div class="pull-left"><strong>'+esacapeHtml(message.userName)+'</strong></div>';
	elMsg += '</div>';
	elMsg += '<div class="clearfix">'+esacapeHtml(message.content)+'</div>';
	elMsg += '</div>';
	elMsg += '</li>';
	if (after) {
		$("#message_list_ul").append(elMsg);
		//$("#message_list_div").scrollTo('#message-'+message.id, 800);
		$("#message_list_div").scrollTop(function() {return this.scrollHeight; });
	} else {
		$("#message_list_ul").prepend(elMsg);
	}
}

function addPreviosMessageLink(message) {
	var elMsg = '<li class="left clearfix" id="more_message" data-ts="'+message.timestamp+'">';
	elMsg += '<div class="clearfix">';
	elMsg += '<a class="btn btn-default">more...</a>';
	elMsg += '</div>';
	elMsg += '</li>';
	$("#message_list_ul").prepend(elMsg);
}

function showLastMessages() {
	fetchSelectedChatRoom(null, function(res) {
    	$("#message_list_ul").empty();
    	if (res.nextMessage != null) {
    		addPreviosMessageLink(res.nextMessage);
    	}
		res.messages.reverse();
    	res.messages.forEach(function(message){addMessage(message, true)});
    	$(".message_section").removeClass("hidden");
    });
}

function showPreviosMessages(timestamp) {
	fetchSelectedChatRoom(timestamp, function(res) {
    	res.messages.forEach(function(message){addMessage(message, false)});
    	if (res.nextMessage != null) {
    		addPreviosMessageLink(res.nextMessage);
    	}
    });
}

function fetchSelectedChatRoom(timestamp, resultCallback) {
    $.ajax({
    	type: "GET",
        url : AJAX_URL_API + '/messages/' + selRoomId + (timestamp ? '?fromTimestamp=' + timestamp : ''),
        cache : false,
        success : resultCallback,
        error: function (jqXHR, exception, errorThrown) {
            var msg = '';
            if (jqXHR.status === 0) {
                msg = 'Not connect.\n Verify Network.';
            } else if (jqXHR.status == 404) {
                msg = 'Requested page not found. [404]';
            } else if (jqXHR.status == 500) {
                msg = 'Internal Server Error [500].' ;
            } else if (exception === 'parsererror') {
                msg = 'Requested JSON parse failed.';
            } else if (exception === 'timeout') {
                msg = 'Time out error.';
            } else if (exception === 'abort') {
                msg = 'Ajax request aborted.';
            } else {
                msg = 'Uncaught Error.\n' + jqXHR.responseText;
            }
    		BootstrapAlert.alert({
  			  title: "STOMP error",
  			  message: msg,
  			  dissmissible: true,
  			  autoHide: false,
  			  hideTimeout: 1000
  			});
        },        
    });
}

$(function() {
//	$("#chatRoomNewForm").on('submit', function(e) {
//		e.preventDefault();
//	});
	connect();
	
	$("#sendNewChatRoom").click(function() {
		sendNewChatRoom();
	});
	
	$("#sendNewMessage").click(function() {
		sendNewMessage();
	});
	
	$("sendNewMessage").keypress(function(e) {
	    if(e.which == 13) {
			sendNewMessage();
	    }
	});
	
	$("#chatroom_list_ul").on("click", "li", function(){
		selRoomId = $(this).data('id');
		$(document).find("#chatroom-count-"+selRoomId).html("");
		showLastMessages();
	});
	
	$("#message_list_ul").on("click", "#more_message", function(){
		var timestamp = $(this).data('ts');
		this.remove();
		showPreviosMessages(timestamp);
	});

});

