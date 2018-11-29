var prefix = "/wxmp/mpMenu";
$(function () {

    var appId = $('.currentMpInfo', window.parent.document).attr('data-appid');
    console.log(appId)
    if (appId) {
        console.log('mpFans 获取appId:' + appId);
        load();
    } else {
        console.log('mpfans 获取appId为空')
    }
});

function load() {
    $('#exampleTable')
        .bootstrapTreeTable(
            {
                id: 'id',
                code: 'id',
                parentCode: 'parentidx',
                type: "GET", // 请求数据的ajax类型
                url: prefix + '/list?appId=' + $('.currentMpInfo', window.top.document).attr('data-appid'), // 请求数据的ajax的url
                ajaxParams: {}, // 请求数据的ajax的data属性
                expandColumn: '1', // 在哪一列上面显示展开按钮
                striped: true, // 是否各行渐变色
                bordered: true, // 是否显示边框
                expandAll: true, // 是否全部展开
                // toolbar : '#exampleToolbar',
                columns: [
                    {
                        title: '编号',
                        field: 'id',
                        visible: false,
                        align: 'center',
                        valign: 'middle',
                        width: '50px'
                    },
                    {
                        field: 'menuname',
                        title: '菜单名',
                        formatter: function (value, row, index) {
                            if (0 != value.parentidx) {
                                return "<span class='text-warning' style='margin-left: -15px;'>|-- </span>  " + value.menuname;
                            }
                            return value.menuname;
                        }
                    },
                    {
                        field: 'menutype',
                        title: '菜单类型',
                        formatter: function (value, row, index) {
                            switch (value.menutype) {
                                case "1":
                                    return '主菜单';
                                case "2":
                                    return '链接';
                                case "3":
                                    return '文本';
                                case "4":
                                    return '关键字';
                                case "5":
                                    return '扫码';
                                case "6":
                                    return '发图片';
                                case "7":
                                    return '发位置';
                                default:
                                    return '主菜单';
                            }
                        }
                    },
                    {
                        field: 'menuurl',
                        title: '菜单链接'
                    },
                    {
                        field: 'replyContent',
                        title: '菜单内容'
                    },
                    {
                        field: 'status',
                        title: '菜单状态',
                        formatter: function (value, row, index) {
                            if (1 == value.status) {
                                return "显示";
                            }
                            return "隐藏";
                        }
                    },
                    {
                        field: 'sort',
                        title: '菜单排序'
                    },
                    {
                        title: '操作',
                        field: 'id',
                        align: 'center',
                        formatter: function (item, index) {
                            var e = '<a class="btn btn-info btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                                + item.id
                                + '\')"><i class="fa fa-edit"></i></a> ';
                            var a = '<a class="btn btn-primary btn-sm ' + s_add_h + '" href="#" title="增加下級"  mce_href="#" onclick="add(\''
                                + item.id
                                + '\')"><i class="fa fa-plus"></i></a> ';
                            var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="removeone(\''
                                + item.id
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            if (item.parentidx == 0) {
                                return e + d + a;
                            }
                            return e + d;
                        }
                    }]
            });
}

function reLoad() {
    load();
}

function add(pId) {
    layer.open({
        type: 2,
        title: '增加',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/add/' + pId
    });
}


function sync() {
    // TODO
    layer.confirm("确认要同步公众号菜单吗?", {
        btn: ['确定', '取消']
    }, function () {
        $.post("/wx/mp/menu/api/sync?appId=" + $('.currentMpInfo', window.top.document).attr('data-appid'), function (res) {
            layer.msg(res.msg);
        });
    });
}

function add(pId) {
    layer.open({
        type: 2,
        title: '增加',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/add/' + pId
    });
}

function edit(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/edit/' + id // iframe的url
    });
}

function removeone(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/remove",
            type: "post",
            data: {
                'id': id
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}

function resetPwd(id) {
}

function batchRemove() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的数据");
        return;
    }
    layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function (i, row) {
            ids[i] = row['id'];
        });
        $.ajax({
            type: 'POST',
            data: {
                "ids": ids
            },
            url: prefix + '/batchRemove',
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {
    });
}

