package com.deliverez.com.tools;

import android.content.Context;
import android.provider.Settings;

import com.deliverez.com.R;
import com.deliverez.com.imagePicker.imageModel.ImageModel;
import com.deliverez.com.init.DeliverezApplication;
import com.deliverez.com.models.Category;
import com.deliverez.com.models.GameName;

import java.util.ArrayList;

public class DeliverezConstants {

    public static final String PLATFORM = "android";

    public final static int ACCESS_STORAGE_PERMISSION = 1;
    public final static int ACCESS_CAMERA_PERMISSION = 2;
    public static int RESULT_LOAD_IMAGE = 1;

    public static final ArrayList<String> FORMAT_IMAGE = new ImageTypeList();
    public static ArrayList<ImageModel> selctedImagesGallery = new ArrayList<>();
    public static ArrayList<ImageModel> selctedImagesCamera = new ArrayList<>();

   public static String android_id = Settings.Secure.getString(DeliverezApplication.getContext().getContentResolver(),
            Settings.Secure.ANDROID_ID);

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    public static final String categoryListNames[] = {"Grocery","Vegetables & Fruits","Dairy","Service Provider","Restaurants"};
    public static Integer categoryImgs[]={R.drawable.grocery,R.drawable.vegetables,
            R.drawable.dairy,R.drawable.service_provider,R.drawable.restaurant};


    public static final String vegiAndFruitSubCatNameList[] = {"Fruits","Frozen","Canned"};
    public static Integer vegiAndFruitSubCatImgs[]={R.drawable.fruits,R.drawable.frozen,R.drawable.canned};

    public static final String dairySubCatNameList[] = {"Milk","Cheese","Curds","Butter","Ghee","Butter Milk","Mawa","Milk","Cheese","Curds","Butter","Ghee","Butter Milk","Mawa"};
    public static Integer dairySubCatImgs[]={R.drawable.milk,R.drawable.cheese,R.drawable.curds,
            R.drawable.butter,R.drawable.ghee,R.drawable.buttermilk,R.drawable.mawa,R.drawable.milk,R.drawable.cheese,R.drawable.curds,
            R.drawable.butter,R.drawable.ghee,R.drawable.buttermilk,R.drawable.mawa};


    public static final String serviceNameList[] = {"Carpenter","Electrician","Plumber","Mounting Service","Painters","Tiles","Construction"};
    public static Integer serviceSubCatImgs[]={R.drawable.carpenter,R.drawable.electrician,R.drawable.plumber,
            R.drawable.mounting_service,R.drawable.painters,R.drawable.tiles,R.drawable.construction};


    public static final String restaurantSubItemsName[] = {"Main Course","Starter","Appetizers","Combos","Sides","Drinks","Breads","Rice","Desert"};
    public static Integer restaurantSubItemsImgs[]={R.drawable.milk,R.drawable.cheese,R.drawable.curds,
            R.drawable.butter,R.drawable.ghee,R.drawable.buttermilk,R.drawable.mawa,R.drawable.milk,R.drawable.cheese};


    public static ArrayList<Category> prepareData(String[] nameList, Integer[] imges) {
        ArrayList<Category> arrayList = new ArrayList<>();
        for (int i = 0; i < nameList.length; i++) {
            Category category = new Category();
            category.setGameName(nameList[i]);
            category.setImage(imges[i]);
            arrayList.add(category);
        }
        return arrayList;
    }


    static class ImageTypeList extends ArrayList<String> {
        ImageTypeList() {
            add(".PNG");
            add(".JPEG");
            add(".jpg");
            add(".png");
            add(".jpeg");
            add(".JPG");
        }
    }

    public static String getDeviceId(){
       return Settings.Secure.getString(DeliverezApplication.getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }


}
