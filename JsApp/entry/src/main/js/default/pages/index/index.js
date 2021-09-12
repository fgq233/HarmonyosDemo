export default {
    data: {
        description: '瑞信下午茶\n旨在为员工创设一个轻松愉快的环境 \n一边喝茶、喝咖啡, 一边进行培训交流\n主题多样，有技术交流,也有学术研讨\n会有公司内部员工的经验分享\n也会邀请知名学者、专家的专题培训\n 瑞信下午茶也会不定期向社会开放持续关注微信公众号的消息哦',
        imageList: ['/common/item_000.png', '/common/item_001.png', '/common/item_002.png', '/common/item_003.png'],
    },

    swipeToIndex(index) {
        this.$element('swiperImage').swipeTo({index: index});
    }
}