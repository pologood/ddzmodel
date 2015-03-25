/**
 * Created by landy on 2015/3/22.
 */
var GameOverLayer = cc.Layer.extend({
    ctor: function () {
        this._super();
        this.init();
    },
    init: function () {
        this._super();
        var winSize = cc.director.getWinSize();

        var bgColor = new cc.LayerColor(cc.color(0, 0, 0, 180), winSize.width, winSize.height);

        this.addChild(bgColor);

        var centerPos = cc.p(winSize.width / 2, winSize.height / 2);
        cc.MenuItemFont.setFontSize(30);
        var menuItemRestart = new cc.MenuItemSprite(
            new cc.Sprite(res.restart_n_png),
            new cc.Sprite(res.restart_s_png),
            this.onRestart, this
        );
        var menu = new cc.Menu(menuItemRestart);
        menu.setPosition(centerPos);
        this.addChild(menu);
    },
    onRestart: function (sender) {
        cc.director.resume();
        cc.director.runScene(new PlayScene());

        //cc.rect
    }
});