package uk.com.v3softech.online.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class order {

    public String order_id;
    public ArrayList<String> order_detail;
    public String order_date;
    public String order_status;
    public String order_time;
    public String payment_method;
    public String total;

    public order(){

    }
    public order(String order_id, ArrayList<String> order_detail, String order_date, String order_status, String order_time, String payment_method, String total) {
        this.order_id = order_id;
        this.order_detail = order_detail;
        this.order_date = order_date;
        this.order_status = order_status;
        this.order_time = order_time;
        this.payment_method = payment_method;
        this.total = total;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public ArrayList<String> getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(ArrayList<String> order_detail) {
        this.order_detail = order_detail;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
