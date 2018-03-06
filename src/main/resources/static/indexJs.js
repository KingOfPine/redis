/**
 * Created by liangsongying on 2017/11/20.
 */

$("#userList").bootstrapTable({
    url: '../test/user',
    method: 'get',
    toolbar: '#table2ToolBar',
    striped: false,
    cache: false,
    pagination: true,//是否显示分页
    paginationPreText: '上一页',
    paginationNextText:'下一页',
    // sortable: true,//是否启用排序
    // sortOrder: 'desc',//排序方式
    // sortName: 'created_time',
    sidePagination: 'server',//分页方式，client，server
    pageNumber: 1,//初始化加载第一页，默认第一页
    pageSize: 10,//每页记录函数
    //pageList: [5,10,15,20],//可供选择的每页行数
    search: true,//是否显示表格搜索，次搜索是客户端搜索
    strictSearch: false,//严格搜索
    //searchOnEnterKey: true,//按下回车键之后才进行搜索
    //showColumns: true,//是否显示所有列
    showRefresh: true,//是否显示刷新按钮
    minimumCountColumns: 3,  //最少允许列数
    clickToSelect: true,//是否启用点击选中行
    //height: 495,
    uniqueId: 'id', //每一行的唯一标识
    cardView: false,//是否显示详细视图
    detailView: false,//是否显示父子表
    showExport: false,
    columns: [
        {
            checkbox: true
        },{
            title: '编号',
            field: 'id'
        },{
            title: '用户帐号',
            field: 'username'
        },
        {
            title: '用户名称',
            field: 'nickname'
        },{
            title: '状态',
            field: 'status'
        }
    ]
});

$("#addUser").click(function () {
    var username=$("#username").val();
    var password = $("#password").val();
    var nickname = $("#nickname").val();
    var user = {};
    user.username = username;
    user.password = password;
    user.nickname = nickname;
    $.ajax({
        url: '../pine/test/add',
        method: 'post',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(user),
        success: function (res) {
            if(res){
                $("#userList").bootstrapTable("refresh");
            }else{

            }
        },
        error: function () {

        }
    })
});
$("#table2ToolBar .delete").click(function () {
    var selUser = $("#userList").bootstrapTable('getSelections');
    var rowId=[];
    for(var i = 0 ; i < selUser.length ; i++){
        rowId.push(selUser[i].id) ;
    }
    $.ajax({
        url: '../pine/test/delete',
        method: 'post',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(rowId),
        success: function (res) {
            if(res){
                $("#userList").bootstrapTable("refresh");
            }else {

            }
        },
        error: function () {
        }
    });
});
