var express = require('express');
var router = express.Router();
var dateFormat = require('dateformat');

router.post("/200", (req, response) => {
	response.status( 200 );
	response.end('OK\n');
 });

router.get("/200", (req, response) => {
	response.status( 200 );
	response.end('OK\n');
 });


router.get("/CTMC", (req, response) => {
	response.writeHead(200, {'Content-Type': 'text/xml'});
  	response.end(createSDIResponse('CTMC'));
});

router.get("/CTMC", (req, response) => {
	response.writeHead(200, {'Content-Type': 'text/xml'});
	response.end(createSDIResponse('CTMC'));
});

router.get("/SCTMC", (req, response) => {
	response.writeHead(200, {'Content-Type': 'text/xml'});
  	response.end(createSDIResponse('SCTMC'));
});

router.get("/SCTMC", (req, response) => {
	response.writeHead(200, {'Content-Type': 'text/xml'});
	response.end(createSDIResponse('SCTMC'));
});

router.get("/DTMCS", (req, response) => {
	response.writeHead(200, {'Content-Type': 'text/xml'});
  	response.end(createSDIResponse('DTMCS'));
});

router.get("/DTMCS", (req, response) => {
	response.writeHead(200, {'Content-Type': 'text/xml'});
	response.end(createSDIResponse('DTMCS'));
});


function createSDIResponse(handler){
	
	let timestamp=dateFormat(new Date(), "yyyymmddHHMMss");
	return  '<ResultData>\n'+
		'\t<TransactionId>'+parseInt((Math.random() * (2000000000 - 1000000) + 1000000),10)+'</TransactionId>\n'+
		'\t<InterfaceId>'+handler+'</InterfaceId>\n'+
		'\t<Result>0</Result>\n'+
		'\t<ResultTime>'+timestamp+'</ResultTime>\n'+
		'\t<Info>\n'+
		'\t</Info>\n'+
		'\t<ResultType>ACK</ResultType>\n'+
		'</ResultData>\n';
}

module.exports = router;
