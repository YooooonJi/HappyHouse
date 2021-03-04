<template>
  <b-row>
    <b-col cols="2"> </b-col>
    <b-col cols="8">
      <card>
        <b-row align-v="center" slot="header">
          <b-col cols="8">
            <h3 class="mb-0">내 정보</h3>
          </b-col>
          <b-col cols="4" class="text-right">
            <router-link to="/editprofile" class="btn btn-sm btn-primary">
              정보 수정
            </router-link>
          </b-col>
        </b-row>

        <b-form @submit.prevent="updateProfile">
          <h6 class="heading-small text-muted mb-4">User information</h6>

          <div class="pl-lg-4">
            <b-row>
              <b-col lg="6">
                <base-input type="text" label="ID" :value="user.userId">
                </base-input>
              </b-col>
              <b-col lg="6">
                <base-input type="text" label="Username" :value="user.userName">
                </base-input>
              </b-col>
            </b-row>
            <b-row>
              <b-col lg="6">
                <base-input
                  type="email"
                  label="Email address"
                  :value="user.userEmail"
                >
                </base-input>
              </b-col>
              <b-col lg="6">
                <base-input type="text" label="Phone" :value="user.userPhone">
                </base-input>
              </b-col>
            </b-row>
          </div>
        </b-form>
      </card>

      <br />
      <br /><br />
      <br /><br />
      <br />
    </b-col>
    <b-col cols="2"> </b-col>
  </b-row>
</template>
<script>
import { mapGetters } from "vuex";
// import http from "../../../util/http-common";
import axios from "axios";
const SERVER_URL = "http://localhost:8080/";
export default {
  data() {
    return {
      user: {
        userId: "",
        userName: "",
        userPassword: "",
        userEmail: "",
        userPhone: ""
      }
    };
  },
  computed: {
    ...mapGetters(["getAccessToken"])
  },
  created() {
    axios
      .get(`${SERVER_URL}user/info`, {
        headers: {
          "auth-token": this.$store.getters.getAccessToken
        }
      })
      .then(response => {
        console.log("gmlgml");
        this.user = response.data.user;
      })
      .catch(() => {
        this.$store.dispatch("LOGOUT").then(() => this.$router.replace("/"));
      });
  },
  methods: {
    // updateProfile() {
    //   alert("Your data: " + JSON.stringify(this.user));
    // }
  }
};
</script>
<style></style>
