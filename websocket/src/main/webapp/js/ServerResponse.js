ServerResponse = {};

ServerResponse.processResponse = function (ctx,e) {
    var parse = JSON.parse(e.data);
    switch (parse.no) {
        
        case 0: //匿名登陆
            ServerResponse.AnonymousLogin(ctx,JSON.parse(parse.data));
            break;
        
        case 1: //加入桌子
            ServerResponse.JoinTable(ctx,JSON.parse(parse.data));
            break;
        
        case 2: //举手
            ServerResponse.RaiseHand(ctx,JSON.parse(parse.data));
            break;
        
        case 3: //发牌
            ServerResponse.PublishCards(ctx,JSON.parse(parse.data));
            break;
        
        case 4: //通知叫地主
            ServerResponse.NotifyCallDealer(ctx,JSON.parse(parse.data));
            break;
        
        case 5: //叫地主
            ServerResponse.CallDealer(ctx,JSON.parse(parse.data));
            break;
        
    }
}