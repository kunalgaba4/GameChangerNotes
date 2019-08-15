package com.goodcompany.gamechangernotes.Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemCreateList {
        @SerializedName("item_name")
        @Expose
        private String itemName;

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }


}
