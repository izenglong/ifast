$(function () {
    //计算元素集合的总宽度
    function calSumWidth(elements) {
        var width = 0;
        $(elements).each(function () {
            width += $(this).outerWidth(true);
        });
        return width;
    }

    //滚动到指定选项卡
    function scrollToTab(element) {
        var marginLeftVal = calSumWidth($(element).prevAll()), marginRightVal = calSumWidth($(element).nextAll());
        // 可视区域非tab宽度
        var $contentTabs = $(".content-tabs");
        var tabOuterWidth = calSumWidth($contentTabs.children().not(".J_menuTabs"));
        //可视区域tab宽度
        var visibleWidth = $contentTabs.outerWidth(true) - tabOuterWidth;
        //实际滚动宽度
        var scrollVal = 0;
        var $pageTabsContent = $(".page-tabs-content");
        if ($pageTabsContent.outerWidth() < visibleWidth) {
            scrollVal = 0;
        } else if (marginRightVal <= (visibleWidth - $(element).outerWidth(true) - $(element).next().outerWidth(true))) {
            if ((visibleWidth - $(element).next().outerWidth(true)) > marginRightVal) {
                scrollVal = marginLeftVal;
                var tabElement = element;
                while ((scrollVal - $(tabElement).outerWidth()) > ($pageTabsContent.outerWidth() - visibleWidth)) {
                    scrollVal -= $(tabElement).prev().outerWidth();
                    tabElement = $(tabElement).prev();
                }
            }
        } else if (marginLeftVal > (visibleWidth - $(element).outerWidth(true) - $(element).prev().outerWidth(true))) {
            scrollVal = marginLeftVal - $(element).prev().outerWidth(true);
        }
        $pageTabsContent.animate({
            marginLeft: 0 - scrollVal + 'px'
        }, "fast");
    }

    //查看左侧隐藏的选项卡
    function scrollTabLeft() {
        var $pageTabsContent = $('.page-tabs-content');
        var marginLeftVal = Math.abs(parseInt($pageTabsContent.css('margin-left')));
        // 可视区域非tab宽度
        var $contentTabs = $(".content-tabs");
        var tabOuterWidth = calSumWidth($contentTabs.children().not(".J_menuTabs"));
        //可视区域tab宽度
        var visibleWidth = $contentTabs.outerWidth(true) - tabOuterWidth;
        //实际滚动宽度
        var scrollVal = 0;
        if ($pageTabsContent.width() < visibleWidth) {
            return false;
        } else {
            var tabElement = $(".J_menuTab:first");
            var offsetVal = 0;
            while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) {//找到离当前tab最近的元素
                offsetVal += $(tabElement).outerWidth(true);
                tabElement = $(tabElement).next();
            }
            offsetVal = 0;
            if (calSumWidth($(tabElement).prevAll()) > visibleWidth) {
                while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
                    offsetVal += $(tabElement).outerWidth(true);
                    tabElement = $(tabElement).prev();
                }
                scrollVal = calSumWidth($(tabElement).prevAll());
            }
        }
        $pageTabsContent.animate({
            marginLeft: 0 - scrollVal + 'px'
        }, "fast");
    }

    //查看右侧隐藏的选项卡
    function scrollTabRight() {
        var $pageTabsContent = $('.page-tabs-content');
        var marginLeftVal = Math.abs(parseInt($pageTabsContent.css('margin-left')));
        // 可视区域非tab宽度
        var $contentTabs = $(".content-tabs");
        var tabOuterWidth = calSumWidth($contentTabs.children().not(".J_menuTabs"));
        //可视区域tab宽度
        var visibleWidth = $contentTabs.outerWidth(true) - tabOuterWidth;
        //实际滚动宽度
        var scrollVal = 0;
        if ($pageTabsContent.width() < visibleWidth) {
            return false;
        } else {
            var tabElement = $(".J_menuTab:first");
            var offsetVal = 0;
            while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) {//找到离当前tab最近的元素
                offsetVal += $(tabElement).outerWidth(true);
                tabElement = $(tabElement).next();
            }
            offsetVal = 0;
            while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
                offsetVal += $(tabElement).outerWidth(true);
                tabElement = $(tabElement).next();
            }
            scrollVal = calSumWidth($(tabElement).prevAll());
            if (scrollVal > 0) {
                $pageTabsContent.animate({
                    marginLeft: 0 - scrollVal + 'px'
                }, "fast");
            }
        }
    }

    //通过遍历给菜单项加上data-index属性
    $(".J_menuItem").each(function (index) {
        if (!$(this).attr('data-index')) {
            $(this).attr('data-index', index);
        }
    });


    function menuItem() {
        // 获取标识数据
        var dataIndex = $(this).data('index');
        var dataUrl = $(this).attr('href'),
            menuName = $.trim($(this).text()),
            flag = true;
        if (dataUrl == undefined || $.trim(dataUrl).length == 0) return false;

        // 微信公众号菜单时，初始化选择公众号
        var currentMp = $('.currentMp');
        if (isWxMpUrl(dataUrl)) {

            console.log('公众号菜单. URL：' + dataUrl);
            currentMp.removeClass('hidden');

            var currentMpInfo = $('.currentMpInfo');
            var appId = currentMpInfo.attr('data-appid');
            if (!appId) {
                console.log('公众号为空，准备初始化...')
                var $mpList01 = $(".mpList li:nth-child(1) a");
                console.log($mpList01);
                console.log($mpList01.attr('data-appid'));
                currentMpInfo.text($mpList01.text());
                currentMpInfo.attr('data-appid', $mpList01.attr('lang'));
            }

            dataUrl += "?appId=" + currentMpInfo.attr('data-appid');

        } else {
            console.log('非公众号菜单. URL：' + dataUrl);
            if (!currentMp.hasClass('hidden')) {
                currentMp.addClass('hidden');
            }
        }

        console.log('选项卡菜单已存在');
        $('.J_menuTab').each(function () {
            console.log('当前 - ' + $(this).data('id') + ' - ' + dataUrl)
            if ($(this).data('id') == dataUrl) {
                console.log('处理 - ' + dataUrl);
                console.log($(this));

                if (!$(this).hasClass('active')) {
                    $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
                    scrollToTab(this);
                    // 显示tab对应的内容区
                    $('.J_mainContent .J_iframe').each(function () {
                        console.log('当前 iframe - ' + $(this).data('id') + ' - ' + dataUrl)
                        if ($(this).data('id') == dataUrl) {
                            console.log('处理 iframe - ' + dataUrl);
                            $(this).show().siblings('.J_iframe').hide();


                            console.log($(this));
                            console.log('src:' + $(this).attr('src'))


                            return false;
                        }
                    });
                } else {
                    console.log('如果是公众号tab && 公众号已经切换，reload');
                    if (isWxMpUrl(dataUrl) && wxMpHasChange) {

                        $('.J_mainContent .J_iframe').each(function () {
                            if ($(this).data('id') == dataUrl) {
                                $(this).show().siblings('.J_iframe').hide();
                                $(this).attr('src', $(this).attr('src'));
                            }
                        });
                        setCurrentWxMpInfo2Last();

                    }

                }
                flag = false;
                return false;
            }
        });

        if (flag) {
            console.log('选项卡菜单不存在')
            var str = '<a href="javascript:;" class="active J_menuTab" data-id="' + dataUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a>';
            $('.J_menuTab').removeClass('active');


            // 添加选项卡对应的iframe
            var str1 = '<iframe class="J_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
            $('.J_mainContent').find('iframe.J_iframe').hide().parents('.J_mainContent').append(str1);

            //显示loading提示
//            var loading = layer.load();
//
//            $('.J_mainContent iframe:visible').load(function () {
//                //iframe加载完成后隐藏loading提示
//                layer.close(loading);
//            });
            // 添加选项卡
            $('.J_menuTabs .page-tabs-content').append(str);
            scrollToTab($('.J_menuTab.active'));
        }
        return false;
    }

    $('.J_menuItem').on('click', menuItem);

    /**
     * 是否为公众号URL
     * @param url
     * @returns {boolean}
     */
    function isWxMpUrl(url) {
        return url.indexOf('/wxmp/') != -1;
    }

    /**
     * 公众号是否切换
     * @returns {boolean}
     */
    function wxMpHasChange() {
        var currentMpInfo = $('.currentMpInfo');
        var lastMpInfo = $('.lastMpInfo');
        console.log('wxMpHasChange: ' + currentMpInfo.attr('data-appid') + ' - ' + lastMpInfo.attr('data-appid'));
        var hasChange = currentMpInfo.attr('data-appid') != lastMpInfo.attr('data-appid');
        console.log('hasChange:' + hasChange);
        return hasChange;
    }

    function setCurrentWxMpInfo2Last() {
        var currentMpInfo = $('.currentMpInfo');
        var lastMpInfo = $('.lastMpInfo');

        lastMpInfo.text(currentMpInfo.text());
        lastMpInfo.attr('data-appid', currentMpInfo.attr('data-appid'));
    }

    /**
     * 切换公众号
     */
    $('.dropdown-menu.mpList li a').on('click', function () {
        var that = $(this);
        var appId = that.attr('lang');
        var mpName = that.text();

        console.log('当前appId：' + appId);
        console.log('当前公众号名称：' + mpName);

        var currentMpInfo = $('.currentMpInfo');
        var lastMpInfo = $('.lastMpInfo');

        lastMpInfo.text(currentMpInfo.text());
        lastMpInfo.attr('data-appid', currentMpInfo.attr('data-appid'));

        currentMpInfo.text(mpName);
        currentMpInfo.attr('data-appid', appId);

        console.log(currentMpInfo);

        // 获取当前激活的选项卡，如果为公众号菜单，则reload
        var $JMenuItem = $('.J_menuTab.active');
        console.log($JMenuItem);
        var dataUrl = $JMenuItem.attr('data-id');
        if (isWxMpUrl(dataUrl)) {
            console.log('当前激活的tab为公众号菜单，reload');
            if (wxMpHasChange()) {
                $('.J_mainContent .J_iframe').each(function () {
                    if ($(this).data('id') == dataUrl) {
                        $(this).show().siblings('.J_iframe').hide();
                        if(dataUrl.indexOf('/wxmp/mpConfig/edit') != -1){
                            var newUrl = $(this).attr('src').slice(0, $(this).attr('src').indexOf('?')) + '?appId=' + appId;
                            $(this).attr('src', newUrl);
                        }else{
                            $(this).attr('src', $(this).attr('src'));
                        }
                    }
                });
            } else {
                console.log('wxMpHasChange: false')
            }
        } else {
            console.log('当前激活的tab非公众号菜单，无需reload');

        }


    });


    // 关闭选项卡菜单
    function closeTab() {
        var closeTabId = $(this).parents('.J_menuTab').data('id');
        var currentWidth = $(this).parents('.J_menuTab').width();

        // 当前元素处于活动状态
        if ($(this).parents('.J_menuTab').hasClass('active')) {

            // 当前元素后面有同辈元素，使后面的一个元素处于活动状态
            if ($(this).parents('.J_menuTab').next('.J_menuTab').size()) {

                var activeId = $(this).parents('.J_menuTab').next('.J_menuTab:eq(0)').data('id');
                $(this).parents('.J_menuTab').next('.J_menuTab:eq(0)').addClass('active');

                $('.J_mainContent .J_iframe').each(function () {
                    if ($(this).data('id') == activeId) {
                        $(this).show().siblings('.J_iframe').hide();
                        return false;
                    }
                });

                var marginLeftVal = parseInt($('.page-tabs-content').css('margin-left'));
                if (marginLeftVal < 0) {
                    $('.page-tabs-content').animate({
                        marginLeft: (marginLeftVal + currentWidth) + 'px'
                    }, "fast");
                }

                //  移除当前选项卡
                $(this).parents('.J_menuTab').remove();

                // 移除tab对应的内容区
                $('.J_mainContent .J_iframe').each(function () {
                    if ($(this).data('id') == closeTabId) {
                        $(this).remove();
                        return false;
                    }
                });
            }

            // 当前元素后面没有同辈元素，使当前元素的上一个元素处于活动状态
            if ($(this).parents('.J_menuTab').prev('.J_menuTab').size()) {
                var activeId = $(this).parents('.J_menuTab').prev('.J_menuTab:last').data('id');
                $(this).parents('.J_menuTab').prev('.J_menuTab:last').addClass('active');
                $('.J_mainContent .J_iframe').each(function () {
                    if ($(this).data('id') == activeId) {
                        $(this).show().siblings('.J_iframe').hide();
                        return false;
                    }
                });

                //  移除当前选项卡
                $(this).parents('.J_menuTab').remove();

                // 移除tab对应的内容区
                $('.J_mainContent .J_iframe').each(function () {
                    if ($(this).data('id') == closeTabId) {
                        $(this).remove();
                        return false;
                    }
                });
            }
        }
        // 当前元素不处于活动状态
        else {
            //  移除当前选项卡
            $(this).parents('.J_menuTab').remove();

            // 移除相应tab对应的内容区
            $('.J_mainContent .J_iframe').each(function () {
                if ($(this).data('id') == closeTabId) {
                    $(this).remove();
                    return false;
                }
            });
            scrollToTab($('.J_menuTab.active'));
        }
        return false;
    }

    $('.J_menuTabs').on('click', '.J_menuTab i', closeTab);

    //关闭其他选项卡
    function closeOtherTabs() {
        var $pageTabsContent = $('.page-tabs-content');
        $pageTabsContent.children("[data-id]").not(":first").not(".active").each(function () {
            $('.J_iframe[data-id="' + $(this).data('id') + '"]').remove();
            $(this).remove();
        });
        $pageTabsContent.css("margin-left", "0");
    }

    $('.J_tabCloseOther').on('click', closeOtherTabs);

    //滚动到已激活的选项卡
    function showActiveTab() {
        scrollToTab($('.J_menuTab.active'));
    }

    $('.J_tabShowActive').on('click', showActiveTab);


    // 点击选项卡菜单
    function activeTab() {

        console.log('tab click.');
        var dataUrl = $(this).data('id');
        if (isWxMpUrl(dataUrl) && wxMpHasChange()) {
            console.log('公众号已经切换 && 为公众号tab，数据需要reload');
            $('.J_mainContent .J_iframe').each(function () {
                if ($(this).data('id') == dataUrl) {
                    $(this).show().siblings('.J_iframe').hide();
                    $(this).attr('src', $(this).attr('src'));
                }
            });

            // 设置上个公众号信息，防止下次再刷新
            setCurrentWxMpInfo2Last();

        }

        if (!$(this).hasClass('active')) {
            var currentId = dataUrl;
            // 显示tab对应的内容区
            $('.J_mainContent .J_iframe').each(function () {
                if ($(this).data('id') == currentId) {
                    $(this).show().siblings('.J_iframe').hide();
                    return false;
                }
            });
            $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
            scrollToTab(this);
        }
    }

    $('.J_menuTabs').on('click', '.J_menuTab', activeTab);

    //刷新iframe
    function refreshTab() {
        var target = $('.J_iframe[data-id="' + $(this).data('id') + '"]');
        var url = target.attr('src');
//        //显示loading提示
//        var loading = layer.load();
//        target.attr('src', url).load(function () {
//            //关闭loading提示
//            layer.close(loading);
//        });
    }

    $('.J_menuTabs').on('dblclick', '.J_menuTab', refreshTab);

    // 左移按扭
    $('.J_tabLeft').on('click', scrollTabLeft);

    // 右移按扭
    $('.J_tabRight').on('click', scrollTabRight);

    // 关闭全部
    $('.J_tabCloseAll').on('click', function () {
        var $pageTabsContent = $('.page-tabs-content');
        $pageTabsContent.children("[data-id]").not(":first").each(function () {
            $('.J_iframe[data-id="' + $(this).data('id') + '"]').remove();
            $(this).remove();
        });
        $pageTabsContent.children("[data-id]:first").each(function () {
            $('.J_iframe[data-id="' + $(this).data('id') + '"]').show();
            $(this).addClass("active");
        });
        $pageTabsContent.css("margin-left", "0");
    });

});
