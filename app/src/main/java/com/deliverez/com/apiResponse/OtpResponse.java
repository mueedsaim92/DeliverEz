package com.deliverez.com.apiResponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell on 13/3/18.
 */

public class OtpResponse extends BaseResponse {

    @SerializedName("payload")
    private OtpPayload payload;

    public OtpPayload getPayload() {
        return payload;
    }

    public void setPayload(OtpPayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "otpResponse{" + "payload=" + payload + '}';
    }

    public class OtpPayload {

        @SerializedName("otp")
        private int otp;

        @SerializedName("token")
        private String token;

        public int getOtp() {
            return otp;
        }

        public void setOtp(int otp) {
            this.otp = otp;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }


        @Override
        public String toString() {
            return "OtpPayload{" +"otp=" + otp +", token='" + token + '\'' + '}';
        }
    }

}
