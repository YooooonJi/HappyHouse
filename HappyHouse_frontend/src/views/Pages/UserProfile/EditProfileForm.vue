<template>
  <b-row>
    <b-col cols="2"> </b-col>
    <b-col cols="8">
      <card>
        <b-row align-v="center" slot="header">
          <b-col cols="8">
            <h3 class="mb-0">내 정보</h3>
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
                <base-input
                  type="text"
                  label="Username"
                  v-model="user.userName"
                >
                </base-input>
              </b-col>
            </b-row>
            <b-row>
              <b-col lg="6">
                <base-input
                  type="email"
                  label="Email address"
                  v-model="user.userEmail"
                >
                </base-input>
              </b-col>
              <b-col lg="6">
                <base-input type="text" label="Phone" v-model="user.userPhone">
                </base-input>
              </b-col>
            </b-row>
          </div>
          <b-row align-v="center" slot="header">
            <b-col cols="8"> </b-col>
            <b-col cols="4" class="text-right">
              <base-button type="primary" @click="update"
                >수정 완료</base-button
              >
            </b-col>
          </b-row>
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
  created() {
    axios
      .get(`${SERVER_URL}user/info`, {
        headers: {
          "auth-token": localStorage.getItem("token")
        }
      })
      .then(response => {
        this.user = response.data.user;
      })
      .catch(() => {
        this.$store.dispatch("LOGOUT").then(() => this.$router.replace("/"));
      });
    console.log(user.userEmail);
  },
  computed: {
    nextRoute() {
      return this.$route.params.nextRoute ? this.$route.params.nextRoute : "";
    }
  },
  methods: {
    // updateProfile() {
    //   alert("Your data: " + JSON.stringify(this.user));
    // },
    update: function() {
      console.log(this.user);
      this.$store
        .dispatch("EDITPROFILE", this.user)
        .then(() => this.$router.replace(`/profile`))
        .catch(({ message }) => {
          this.$store.dispatch("LOGOUT").then(() => this.$router.replace("/"));
        });
    }
  }
};
</script>
<style></style>
