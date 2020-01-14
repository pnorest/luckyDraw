$(function () {
    $('#loginOut').click(function(){
        logout();
    });
})
function logout() {
    $.ajax({
        type:'POST',
        url:'/dumps/logout',
        dataType:'text',
        success:function(data){
            window.location.href='/dumps'+data;
        }
    })
}
