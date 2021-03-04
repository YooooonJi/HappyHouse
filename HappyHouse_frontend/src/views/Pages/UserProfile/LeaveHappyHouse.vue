<template>
  <b-row>
    <b-col cols="4"> </b-col>
    <b-col cols="4">
      <card>
        <b-row align-v="center" slot="header">
          <b-col cols="8">
            <h3 class="mb-0">비밀번호 확인</h3>
          </b-col>
        </b-row>

        <b-form @submit.prevent="">
          <h6 class="heading-small text-muted mb-4">
            비밀번호를 입력해주세요.
          </h6>

          <div class="pl-lg-4">
            <b-row>
              <b-col lg="11">
                <base-input
                  type="password"
                  label="userPassword"
                  v-model="user.userPassword"
                >
                </base-input>
              </b-col>
            </b-row>
          </div>
          <b-row align-v="center" slot="header">
            <b-col cols="8"> </b-col>
            <b-col cols="4" class="text-right">
              <base-button type="primary" @click="leave">회원 탈퇴</base-button>
            </b-col>
          </b-row>
        </b-form>
      </card>

      <br />
      <br /><br />
      <br /><br />
      <br />
    </b-col>
    <b-col cols="4"> </b-col>
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
        userId: this.$store.getters.getUserId,
        userPassword: ""
      }
    };
  },
  methods: {
    leave: function() {
      console.log(this.user);
      axios
        .post(`${SERVER_URL}user/leave`, this.user, {
          headers: {
            "auth-token": localStorage.getItem("token")
          }
        })
        .then(response => {
          if (response.data["result"] == "SUCCESS") {
            this.$alertify.success(
              "탈퇴가 완료되었습니다. 메인화면으로 돌아갑니다."
            );
            this.$store
              .dispatch("LOGOUT")
              .then(() => this.$router.replace("/").catch(() => {}));
          } else {
            this.$alertify.error("탈퇴에 문제가 생겼습니다.");
          }
        })
        .catch(() => {
          this.$alertify.error(
            "서버에 문제가 생겼습니다. 로그아웃 후 메인화면으로 돌아갑니다."
          );
          this.$store.dispatch("LOGOUT").then(() => this.$router.replace("/"));
        });
    }
  }
};
</script>
<style></style>
