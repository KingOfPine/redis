/**
 * Created by liangsongying on 2018/2/6.
 */
$(".btn-primary").click(function () {
  var userName= $("#email").val();
  var password= $("#password").val();
    $.ajax({
        url: './login',
        method: 'post',
        dataType: 'json',
        // contentType: 'application/json',
        data:{
            userName:userName,
            password:password
        },success:function (res) {
            if (res.code==1) {
                window.location.href='/redis/test/test';
            }
            else {
                $("#email").val("");
               $("#password").val("");
            }
        }
    });
});
