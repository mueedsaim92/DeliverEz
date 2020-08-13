package com.deliverez.com.apiResponse;

public class LoginResponse extends BaseResponse {
    public String message;
    public Data userdata;


    public class Data {
        public String email;
        public String phoneNumber;
        public String userId;
        public String fullName;
        public String profileImage;
        public String distanceRange;
        public String sharephoneNumber;
        public String activeStatus;
        public String address;
        public String location;
        public String token;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getDistanceRange() {
            return distanceRange;
        }

        public void setDistanceRange(String distanceRange) {
            this.distanceRange = distanceRange;
        }

        public String getSharephoneNumber() {
            return sharephoneNumber;
        }

        public void setSharephoneNumber(String sharephoneNumber) {
            this.sharephoneNumber = sharephoneNumber;
        }

        public String getActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(String activeStatus) {
            this.activeStatus = activeStatus;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public Data getUserdata() {
        return userdata;
    }

    public void setUserdata(Data userdata) {
        this.userdata = userdata;
    }
}
