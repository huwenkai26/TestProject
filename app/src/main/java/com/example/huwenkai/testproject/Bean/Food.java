package com.example.huwenkai.testproject.Bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 */

public class Food {


    /**
     * code : 200
     * data : [{"seat":"A5755 16:04:41","status":"2","pay_status":"2","total_price":"20.00","finish_time":"1490083181","rest_logo":"http://klory-image.b0.upaiyun.com/restlogo/20170221/58ab9f43c8709.jpg","rest_name":"好好吃","pay_time":"1489979429","user_id":"2","time":"1486973081","rest_id":"10","id":"201702139957559898","eating_time":"1490150545","diner":"0","remark":[{"id":"15","order_id":"201702139957559898","food_id":"9","user_id":"2","content":"不要辣","amount":"2","time":"1487063734","status":"1","food_name":"榴莲披萨","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170222/58ad4b4a334e1.png","served":2},{"id":"15","order_id":"201702139957559898","food_id":"9","user_id":"2","content":"不要辣","amount":"2","time":"1487063734","status":"1","food_name":"榴莲披萨","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170222/58ad4b4a334e1.png","served":1},{"id":"14","order_id":"201702139957559898","food_id":"8","user_id":"2","content":"多点辣","amount":"1","time":"1487063734","status":"1","food_name":"空心菜","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170222/58ad3c53347e8.png","served":2}]},{"seat":"不迷茫的桌号 11:10:29","status":"2","pay_status":"2","total_price":"134.60","finish_time":"1490090275","rest_logo":"http://klory-image.b0.upaiyun.com/restlogo/20170221/58ab9f43c8709.jpg","rest_name":"好好吃","pay_time":"1489979429","user_id":"2","time":"1489979429","rest_id":"10","id":"201703209953984854","eating_time":"1490150899","diner":"99","remark":[{"id":"15","order_id":"201702139957559898","food_id":"9","user_id":"2","content":"不要辣","amount":"2","time":"1487063734","status":"1","food_name":"榴莲披萨","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170222/58ad4b4a334e1.png","served":2},{"id":"15","order_id":"201702139957559898","food_id":"9","user_id":"2","content":"不要辣","amount":"2","time":"1487063734","status":"1","food_name":"榴莲披萨","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170222/58ad4b4a334e1.png","served":1},{"id":"14","order_id":"201702139957559898","food_id":"8","user_id":"2","content":"多点辣","amount":"1","time":"1487063734","status":"1","food_name":"空心菜","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170222/58ad3c53347e8.png","served":2},{"id":"119","order_id":"201703209953984854","food_id":"3","user_id":"2","content":null,"amount":"4","time":"1489979429","status":"1","food_name":"大鸡翅","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170223/58ae9fe7568f7.png","served":1},{"id":"119","order_id":"201703209953984854","food_id":"3","user_id":"2","content":null,"amount":"4","time":"1489979429","status":"1","food_name":"大鸡翅","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170223/58ae9fe7568f7.png","served":1},{"id":"119","order_id":"201703209953984854","food_id":"3","user_id":"2","content":null,"amount":"4","time":"1489979429","status":"1","food_name":"大鸡翅","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170223/58ae9fe7568f7.png","served":1},{"id":"119","order_id":"201703209953984854","food_id":"3","user_id":"2","content":null,"amount":"4","time":"1489979429","status":"1","food_name":"大鸡翅","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170223/58ae9fe7568f7.png","served":1},{"id":"118","order_id":"201703209953984854","food_id":"2","user_id":"2","content":null,"amount":"2","time":"1489979429","status":"1","food_name":"小鸡翅","food_image":"","served":2},{"id":"118","order_id":"201703209953984854","food_id":"2","user_id":"2","content":null,"amount":"2","time":"1489979429","status":"1","food_name":"小鸡翅","food_image":"","served":1}]},{"seat":"不迷茫的桌号 11:10:16","status":"2","pay_status":"2","total_price":"134.60","finish_time":"1490091986","rest_logo":"http://klory-image.b0.upaiyun.com/restlogo/20170221/58ab9f43c8709.jpg","rest_name":"好好吃","pay_time":"1489979429","user_id":"2","time":"1489979416","rest_id":"10","id":"201703209956575198","eating_time":"1490150935","diner":"99","remark":[{"id":"15","order_id":"201702139957559898","food_id":"9","user_id":"2","content":"不要辣","amount":"2","time":"1487063734","status":"1","food_name":"榴莲披萨","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170222/58ad4b4a334e1.png","served":2},{"id":"15","order_id":"201702139957559898","food_id":"9","user_id":"2","content":"不要辣","amount":"2","time":"1487063734","status":"1","food_name":"榴莲披萨","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170222/58ad4b4a334e1.png","served":1},{"id":"14","order_id":"201702139957559898","food_id":"8","user_id":"2","content":"多点辣","amount":"1","time":"1487063734","status":"1","food_name":"空心菜","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170222/58ad3c53347e8.png","served":2},{"id":"119","order_id":"201703209953984854","food_id":"3","user_id":"2","content":null,"amount":"4","time":"1489979429","status":"1","food_name":"大鸡翅","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170223/58ae9fe7568f7.png","served":1},{"id":"119","order_id":"201703209953984854","food_id":"3","user_id":"2","content":null,"amount":"4","time":"1489979429","status":"1","food_name":"大鸡翅","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170223/58ae9fe7568f7.png","served":1},{"id":"119","order_id":"201703209953984854","food_id":"3","user_id":"2","content":null,"amount":"4","time":"1489979429","status":"1","food_name":"大鸡翅","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170223/58ae9fe7568f7.png","served":1},{"id":"119","order_id":"201703209953984854","food_id":"3","user_id":"2","content":null,"amount":"4","time":"1489979429","status":"1","food_name":"大鸡翅","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170223/58ae9fe7568f7.png","served":1},{"id":"118","order_id":"201703209953984854","food_id":"2","user_id":"2","content":null,"amount":"2","time":"1489979429","status":"1","food_name":"小鸡翅","food_image":"","served":2},{"id":"118","order_id":"201703209953984854","food_id":"2","user_id":"2","content":null,"amount":"2","time":"1489979429","status":"1","food_name":"小鸡翅","food_image":"","served":1},{"id":"117","order_id":"201703209956575198","food_id":"3","user_id":"2","content":null,"amount":"4","time":"1489979416","status":"1","food_name":"大鸡翅","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170223/58ae9fe7568f7.png","served":2},{"id":"117","order_id":"201703209956575198","food_id":"3","user_id":"2","content":null,"amount":"4","time":"1489979416","status":"1","food_name":"大鸡翅","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170223/58ae9fe7568f7.png","served":1},{"id":"117","order_id":"201703209956575198","food_id":"3","user_id":"2","content":null,"amount":"4","time":"1489979416","status":"1","food_name":"大鸡翅","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170223/58ae9fe7568f7.png","served":1},{"id":"117","order_id":"201703209956575198","food_id":"3","user_id":"2","content":null,"amount":"4","time":"1489979416","status":"1","food_name":"大鸡翅","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170223/58ae9fe7568f7.png","served":1},{"id":"116","order_id":"201703209956575198","food_id":"2","user_id":"2","content":null,"amount":"2","time":"1489979416","status":"1","food_name":"小鸡翅","food_image":"","served":2},{"id":"116","order_id":"201703209956575198","food_id":"2","user_id":"2","content":null,"amount":"2","time":"1489979416","status":"1","food_name":"小鸡翅","food_image":"","served":2}]}]
     * msg :
     */

    public int code;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DataBean bean = (DataBean) o;

            if (seat != null ? !seat.equals(bean.seat) : bean.seat != null) return false;
            if (user_id != null ? !user_id.equals(bean.user_id) : bean.user_id != null)
                return false;
            return id != null ? id.equals(bean.id) : bean.id == null;

        }

        @Override
        public int hashCode() {
            int result = seat != null ? seat.hashCode() : 0;
            result = 31 * result + (user_id != null ? user_id.hashCode() : 0);
            result = 31 * result + (id != null ? id.hashCode() : 0);
            return result;
        }

        /**
         * seat : A5755 16:04:41
         * status : 2
         * pay_status : 2
         * total_price : 20.00
         * finish_time : 1490083181
         * rest_logo : http://klory-image.b0.upaiyun.com/restlogo/20170221/58ab9f43c8709.jpg
         * rest_name : 好好吃
         * pay_time : 1489979429
         * user_id : 2
         * time : 1486973081
         * rest_id : 10
         * id : 201702139957559898
         * eating_time : 1490150545
         * diner : 0
         * remark : [{"id":"15","order_id":"201702139957559898","food_id":"9","user_id":"2","content":"不要辣","amount":"2","time":"1487063734","status":"1","food_name":"榴莲披萨","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170222/58ad4b4a334e1.png","served":2},{"id":"15","order_id":"201702139957559898","food_id":"9","user_id":"2","content":"不要辣","amount":"2","time":"1487063734","status":"1","food_name":"榴莲披萨","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170222/58ad4b4a334e1.png","served":1},{"id":"14","order_id":"201702139957559898","food_id":"8","user_id":"2","content":"多点辣","amount":"1","time":"1487063734","status":"1","food_name":"空心菜","food_image":"http://klory-image.b0.upaiyun.com/foodimage/20170222/58ad3c53347e8.png","served":2}]
         */


        public String seat;
        public String status;
        public String pay_status;
        public String total_price;
        public String finish_time;
        public String rest_logo;
        public String rest_name;
        public String pay_time;
        public String user_id;
        public String time;
        public String rest_id;
        public String id;
        public String eating_time;
        public String diner;
        public List<RemarkBean> remark;

        public static class RemarkBean {
            /**
             * id : 15
             * order_id : 201702139957559898
             * food_id : 9
             * user_id : 2
             * content : 不要辣
             * amount : 2
             * time : 1487063734
             * status : 1
             * food_name : 榴莲披萨
             * food_image : http://klory-image.b0.upaiyun.com/foodimage/20170222/58ad4b4a334e1.png
             * served : 2
             */

            public String id;
            public String order_id;
            public String food_id;
            public String user_id;
            public String content;
            public String amount;
            public String time;
            public String status;
            public String food_name;
            public String food_image;
            public int served;
        }
    }
}
