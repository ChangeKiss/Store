package com.Store.www.entity;

import java.util.List;

/**
 * Created by www on 2018/10/11.
 * 国家编号响应体
 */

public class CountryCodeResponse {

    /**
     * returnValue : 1
     * data : [{"id":1,"country":"中国","prefix":86,"area":"亚洲"},{"id":2,"country":"香港","prefix":852,"area":"亚洲"},{"id":3,"country":"澳门","prefix":853,"area":"亚洲"},{"id":4,"country":"台湾","prefix":886,"area":"亚洲"},{"id":5,"country":"马来西亚","prefix":60,"area":"亚洲"},{"id":6,"country":"印度尼西亚","prefix":62,"area":"亚洲"},{"id":7,"country":"菲律宾","prefix":63,"area":"亚洲"},{"id":8,"country":"新加坡","prefix":65,"area":"亚洲"},{"id":9,"country":"泰国","prefix":66,"area":"亚洲"},{"id":10,"country":"日本","prefix":81,"area":"亚洲"},{"id":11,"country":"韩国","prefix":82,"area":"亚洲"},{"id":12,"country":"塔吉克斯坦","prefix":7,"area":"亚洲"},{"id":13,"country":"哈萨克斯坦","prefix":7,"area":"亚洲"},{"id":14,"country":"越南","prefix":84,"area":"亚洲"},{"id":15,"country":"土耳其","prefix":90,"area":"亚洲"},{"id":16,"country":"印度","prefix":91,"area":"亚洲"},{"id":17,"country":"巴基斯坦","prefix":92,"area":"亚洲"},{"id":18,"country":"阿富汗","prefix":93,"area":"亚洲"},{"id":19,"country":"斯里兰卡","prefix":94,"area":"亚洲"},{"id":20,"country":"缅甸","prefix":95,"area":"亚洲"},{"id":21,"country":"伊朗","prefix":98,"area":"亚洲"},{"id":22,"country":"亚美尼亚","prefix":374,"area":"亚洲"},{"id":23,"country":"东帝汶","prefix":670,"area":"亚洲"},{"id":24,"country":"文莱","prefix":673,"area":"亚洲"},{"id":25,"country":"朝鲜","prefix":850,"area":"亚洲"},{"id":26,"country":"柬埔寨","prefix":855,"area":"亚洲"},{"id":27,"country":"老挝","prefix":856,"area":"亚洲"},{"id":28,"country":"孟加拉国","prefix":880,"area":"亚洲"},{"id":29,"country":"马尔代夫","prefix":960,"area":"亚洲"},{"id":30,"country":"黎巴嫩","prefix":961,"area":"亚洲"},{"id":31,"country":"约旦","prefix":962,"area":"亚洲"},{"id":32,"country":"叙利亚","prefix":963,"area":"亚洲"},{"id":33,"country":"伊拉克","prefix":964,"area":"亚洲"},{"id":34,"country":"科威特","prefix":965,"area":"亚洲"},{"id":35,"country":"沙特阿拉伯","prefix":966,"area":"亚洲"},{"id":36,"country":"也门","prefix":967,"area":"亚洲"},{"id":37,"country":"阿曼","prefix":968,"area":"亚洲"},{"id":38,"country":"巴勒斯坦","prefix":970,"area":"亚洲"},{"id":39,"country":"阿联酋","prefix":971,"area":"亚洲"},{"id":40,"country":"以色列","prefix":972,"area":"亚洲"},{"id":41,"country":"巴林","prefix":973,"area":"亚洲"},{"id":42,"country":"卡塔尔","prefix":974,"area":"亚洲"},{"id":43,"country":"不丹","prefix":975,"area":"亚洲"},{"id":44,"country":"蒙古","prefix":976,"area":"亚洲"},{"id":45,"country":"尼泊尔","prefix":977,"area":"亚洲"},{"id":46,"country":"土库曼斯坦","prefix":993,"area":"亚洲"},{"id":47,"country":"阿塞拜疆","prefix":994,"area":"亚洲"},{"id":48,"country":"乔治亚","prefix":995,"area":"亚洲"},{"id":49,"country":"吉尔吉斯斯坦","prefix":996,"area":"亚洲"},{"id":50,"country":"乌兹别克斯坦","prefix":998,"area":"亚洲"},{"id":51,"country":"英国","prefix":44,"area":"欧洲"},{"id":52,"country":"德国","prefix":49,"area":"欧洲"},{"id":53,"country":"意大利","prefix":39,"area":"欧洲"},{"id":54,"country":"法国","prefix":33,"area":"欧洲"},{"id":55,"country":"俄罗斯","prefix":7,"area":"欧洲"},{"id":56,"country":"希腊","prefix":30,"area":"欧洲"},{"id":57,"country":"荷兰","prefix":31,"area":"欧洲"},{"id":58,"country":"比利时","prefix":32,"area":"欧洲"},{"id":59,"country":"西班牙","prefix":34,"area":"欧洲"},{"id":60,"country":"匈牙利","prefix":36,"area":"欧洲"},{"id":61,"country":"罗马尼亚","prefix":40,"area":"欧洲"},{"id":62,"country":"瑞士","prefix":41,"area":"欧洲"},{"id":63,"country":"奥地利","prefix":43,"area":"欧洲"},{"id":64,"country":"丹麦","prefix":45,"area":"欧洲"},{"id":65,"country":"瑞典","prefix":46,"area":"欧洲"},{"id":66,"country":"挪威","prefix":47,"area":"欧洲"},{"id":67,"country":"波兰","prefix":48,"area":"欧洲"},{"id":68,"country":"圣马力诺","prefix":223,"area":"欧洲"},{"id":69,"country":"匈牙利","prefix":336,"area":"欧洲"},{"id":70,"country":"南斯拉夫","prefix":338,"area":"欧洲"},{"id":71,"country":"直布罗陀","prefix":350,"area":"欧洲"},{"id":72,"country":"葡萄牙","prefix":351,"area":"欧洲"},{"id":73,"country":"卢森堡","prefix":352,"area":"欧洲"},{"id":74,"country":"爱尔兰","prefix":353,"area":"欧洲"},{"id":75,"country":"冰岛","prefix":354,"area":"欧洲"},{"id":76,"country":"阿尔巴尼亚","prefix":355,"area":"欧洲"},{"id":77,"country":"马耳他","prefix":356,"area":"欧洲"},{"id":78,"country":"塞浦路斯","prefix":357,"area":"欧洲"},{"id":79,"country":"芬兰","prefix":358,"area":"欧洲"},{"id":80,"country":"保加利亚","prefix":359,"area":"欧洲"},{"id":81,"country":"立陶宛","prefix":370,"area":"欧洲"},{"id":82,"country":"拉脱维亚","prefix":371,"area":"欧洲"},{"id":83,"country":"爱沙尼亚","prefix":372,"area":"欧洲"},{"id":84,"country":"摩尔多瓦","prefix":373,"area":"欧洲"},{"id":85,"country":"安道尔共和国","prefix":376,"area":"欧洲"},{"id":86,"country":"乌克兰","prefix":380,"area":"欧洲"},{"id":87,"country":"南斯拉夫","prefix":381,"area":"欧洲"},{"id":88,"country":"克罗地亚","prefix":385,"area":"欧洲"},{"id":89,"country":"斯洛文尼亚","prefix":386,"area":"欧洲"},{"id":90,"country":"波黑","prefix":387,"area":"欧洲"},{"id":91,"country":"马其顿","prefix":389,"area":"欧洲"},{"id":92,"country":"梵蒂冈","prefix":396,"area":"欧洲"},{"id":93,"country":"捷克","prefix":420,"area":"欧洲"},{"id":94,"country":"斯洛伐克","prefix":421,"area":"欧洲"},{"id":95,"country":"列支敦士登","prefix":423,"area":"欧洲"},{"id":96,"country":"秘鲁","prefix":51,"area":"南美洲"},{"id":97,"country":"墨西哥","prefix":52,"area":"南美洲"},{"id":98,"country":"古巴","prefix":53,"area":"南美洲"},{"id":99,"country":"阿根廷","prefix":54,"area":"南美洲"},{"id":100,"country":"巴西","prefix":55,"area":"南美洲"},{"id":101,"country":"智利","prefix":56,"area":"南美洲"},{"id":102,"country":"哥伦比亚","prefix":57,"area":"南美洲"},{"id":103,"country":"委内瑞拉","prefix":58,"area":"南美洲"},{"id":104,"country":"福克兰群岛","prefix":500,"area":"南美洲"},{"id":105,"country":"伯利兹","prefix":501,"area":"南美洲"},{"id":106,"country":"危地马拉","prefix":502,"area":"南美洲"},{"id":107,"country":"萨尔瓦多","prefix":503,"area":"南美洲"},{"id":108,"country":"洪都拉斯","prefix":504,"area":"南美洲"},{"id":109,"country":"尼加拉瓜","prefix":505,"area":"南美洲"},{"id":110,"country":"哥斯达黎加","prefix":506,"area":"南美洲"},{"id":111,"country":"巴拿马","prefix":507,"area":"南美洲"},{"id":112,"country":"圣彼埃尔","prefix":508,"area":"南美洲"},{"id":113,"country":"海地","prefix":509,"area":"南美洲"},{"id":114,"country":"瓜德罗普","prefix":590,"area":"南美洲"},{"id":115,"country":"玻利维亚","prefix":591,"area":"南美洲"},{"id":116,"country":"圭亚那","prefix":592,"area":"南美洲"},{"id":117,"country":"厄瓜多尔","prefix":593,"area":"南美洲"},{"id":118,"country":"法属圭亚那","prefix":594,"area":"南美洲"},{"id":119,"country":"巴拉圭","prefix":595,"area":"南美洲"},{"id":120,"country":"马提尼克","prefix":596,"area":"南美洲"},{"id":121,"country":"苏里南","prefix":597,"area":"南美洲"},{"id":122,"country":"乌拉圭","prefix":598,"area":"南美洲"},{"id":123,"country":"埃及","prefix":20,"area":"非洲"},{"id":124,"country":"南非","prefix":27,"area":"非洲"},{"id":125,"country":"摩洛哥","prefix":212,"area":"非洲"},{"id":126,"country":"阿尔及利亚","prefix":213,"area":"非洲"},{"id":127,"country":"突尼斯","prefix":216,"area":"非洲"},{"id":128,"country":"利比亚","prefix":218,"area":"非洲"},{"id":129,"country":"冈比亚","prefix":220,"area":"非洲"},{"id":130,"country":"塞内加尔","prefix":221,"area":"非洲"},{"id":131,"country":"毛里塔尼亚","prefix":222,"area":"非洲"},{"id":132,"country":"马里","prefix":223,"area":"非洲"},{"id":133,"country":"几内亚","prefix":224,"area":"非洲"},{"id":134,"country":"科特迪瓦","prefix":225,"area":"非洲"},{"id":135,"country":"布基拉法索","prefix":226,"area":"非洲"},{"id":136,"country":"尼日尔","prefix":227,"area":"非洲"},{"id":137,"country":"多哥","prefix":228,"area":"非洲"},{"id":138,"country":"贝宁","prefix":229,"area":"非洲"},{"id":139,"country":"毛里求斯","prefix":230,"area":"非洲"},{"id":140,"country":"利比里亚","prefix":231,"area":"非洲"},{"id":141,"country":"塞拉利昂","prefix":232,"area":"非洲"},{"id":142,"country":"加纳","prefix":233,"area":"非洲"},{"id":143,"country":"尼日利亚","prefix":234,"area":"非洲"},{"id":144,"country":"乍得","prefix":235,"area":"非洲"},{"id":145,"country":"中非","prefix":236,"area":"非洲"},{"id":146,"country":"喀麦隆","prefix":237,"area":"非洲"},{"id":147,"country":"佛得角","prefix":238,"area":"非洲"},{"id":148,"country":"圣多美","prefix":239,"area":"非洲"},{"id":149,"country":"普林西比","prefix":239,"area":"非洲"},{"id":150,"country":"赤道几内亚","prefix":240,"area":"非洲"},{"id":151,"country":"加蓬","prefix":241,"area":"非洲"},{"id":152,"country":"刚果","prefix":242,"area":"非洲"},{"id":153,"country":"扎伊尔","prefix":243,"area":"非洲"},{"id":154,"country":"安哥拉","prefix":244,"area":"非洲"},{"id":155,"country":"几内亚比绍","prefix":245,"area":"非洲"},{"id":156,"country":"阿森松","prefix":247,"area":"非洲"},{"id":157,"country":"塞舌尔","prefix":248,"area":"非洲"},{"id":158,"country":"苏丹","prefix":249,"area":"非洲"},{"id":159,"country":"卢旺达","prefix":250,"area":"非洲"},{"id":160,"country":"埃塞俄比亚","prefix":251,"area":"非洲"},{"id":161,"country":"索马里","prefix":252,"area":"非洲"},{"id":162,"country":"吉布提","prefix":253,"area":"非洲"},{"id":163,"country":"肯尼亚","prefix":254,"area":"非洲"},{"id":164,"country":"坦桑尼亚","prefix":255,"area":"非洲"},{"id":165,"country":"乌干达","prefix":256,"area":"非洲"},{"id":166,"country":"布隆迪","prefix":257,"area":"非洲"},{"id":167,"country":"莫桑比克","prefix":258,"area":"非洲"},{"id":168,"country":"赞比亚","prefix":260,"area":"非洲"},{"id":169,"country":"马达加斯加","prefix":261,"area":"非洲"},{"id":170,"country":"留尼旺岛","prefix":262,"area":"非洲"},{"id":171,"country":"津巴布韦","prefix":263,"area":"非洲"},{"id":172,"country":"纳米比亚","prefix":264,"area":"非洲"},{"id":173,"country":"马拉维","prefix":265,"area":"非洲"},{"id":174,"country":"莱索托","prefix":266,"area":"非洲"},{"id":175,"country":"博茨瓦纳","prefix":267,"area":"非洲"},{"id":176,"country":"斯威士兰","prefix":268,"area":"非洲"},{"id":177,"country":"科摩罗","prefix":269,"area":"非洲"},{"id":178,"country":"圣赫勒拿","prefix":290,"area":"非洲"},{"id":179,"country":"厄立特里亚","prefix":291,"area":"非洲"},{"id":180,"country":"阿鲁巴岛","prefix":297,"area":"非洲"},{"id":181,"country":"法罗群岛","prefix":298,"area":"非洲"},{"id":182,"country":"摩纳哥","prefix":377,"area":"非洲"},{"id":183,"country":"澳大利亚","prefix":61,"area":"大洋洲"},{"id":184,"country":"新西兰","prefix":64,"area":"大洋洲"},{"id":185,"country":"关岛","prefix":671,"area":"大洋洲"},{"id":186,"country":"瑙鲁","prefix":674,"area":"大洋洲"},{"id":187,"country":"汤加","prefix":676,"area":"大洋洲"},{"id":188,"country":"所罗门群岛","prefix":677,"area":"大洋洲"},{"id":189,"country":"瓦努阿图","prefix":678,"area":"大洋洲"},{"id":190,"country":"斐济","prefix":679,"area":"大洋洲"},{"id":191,"country":"科克群岛","prefix":682,"area":"大洋洲"},{"id":192,"country":"纽埃岛","prefix":683,"area":"大洋洲"},{"id":193,"country":"东萨摩亚","prefix":684,"area":"大洋洲"},{"id":194,"country":"西萨摩亚","prefix":685,"area":"大洋洲"},{"id":195,"country":"基里巴斯","prefix":686,"area":"大洋洲"},{"id":196,"country":"图瓦卢","prefix":688,"area":"大洋洲"},{"id":197,"country":"科科斯岛","prefix":619162,"area":"大洋洲"},{"id":198,"country":"诺福克岛","prefix":6723,"area":"大洋洲"},{"id":199,"country":"圣诞岛","prefix":619164,"area":"大洋洲"},{"id":200,"country":"美国","prefix":1,"area":"北美洲"},{"id":201,"country":"加拿大","prefix":1,"area":"北美洲"},{"id":202,"country":"夏威夷","prefix":1808,"area":"北美洲"},{"id":203,"country":"阿拉斯加","prefix":1907,"area":"北美洲"},{"id":204,"country":"格陵兰岛","prefix":299,"area":"北美洲"},{"id":205,"country":"中途岛","prefix":1808,"area":"北美洲"},{"id":206,"country":"威克岛","prefix":1808,"area":"北美洲"},{"id":207,"country":"维尔京群岛","prefix":1809,"area":"北美洲"},{"id":208,"country":"波多黎各","prefix":1809,"area":"北美洲"},{"id":209,"country":"巴哈马","prefix":1809,"area":"北美洲"},{"id":210,"country":"安圭拉岛","prefix":1809,"area":"北美洲"},{"id":211,"country":"圣卢西亚","prefix":1809,"area":"北美洲"},{"id":212,"country":"巴巴多斯","prefix":1809,"area":"北美洲"},{"id":213,"country":"牙买加","prefix":1876,"area":"北美洲"},{"id":214,"country":"南极洲","prefix":64672,"area":"南极洲"}]
     */

    private int returnValue;
    private List<DataBean> data;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * country : 中国
         * prefix : 86
         * area : 亚洲
         */

        private int id;
        private String country;
        private int prefix;
        private String area;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getPrefix() {
            return prefix;
        }

        public void setPrefix(int prefix) {
            this.prefix = prefix;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }
    }
}
