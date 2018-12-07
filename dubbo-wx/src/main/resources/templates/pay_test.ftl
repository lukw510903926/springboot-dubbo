<html>
<head>
    <meta http-equiv="content-type" content="text/html;charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>支付测试</title>

    <script>
    function onBridgeReady(){
        WeixinJSBridge.invoke(
                'getBrandWCPayRequest', {
                    "appId": "${appId}",
                    "timeStamp": "${timeStamp}", // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
                    "nonceStr": "${nonceStr}", // 支付签名随机串，不长于 32 位
                    "package": "${package}", // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=\*\*\*）
                    "signType": "${signType}", // 签名方式，默认为'SHA1'，使用新版支付需传入'md5'
                    "paySign": "${paySign}"
                },
                function(res){
                    if(res.err_msg == "get_brand_wcpay_request:ok" ){
                        // 使用以上方式判断前端返回,微信团队郑重提示：
                        //res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
                    }
                }
        );
    }
    if (typeof WeixinJSBridge == "undefined"){
        if( document.addEventListener ){
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        }else if (document.attachEvent){
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    }else{
        onBridgeReady();
    }

</script>
</head>
<body>

<button onclick="onBridgeReady()">支付</button>

</body>
</html>