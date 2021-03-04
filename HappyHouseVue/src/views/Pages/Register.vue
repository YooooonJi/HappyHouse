<template>

  <div>
    <!-- Header -->
    <!-- <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9"> -->
    <div
      class="header pb-8 pt-5 pt-lg-8 d-flex align-items-center profile-header"
      style="min-height: 400px; background-image: url(img/theme/profile-cover.jpg); background-size: cover; background-position: center top;"
    >
      <b-container class="container">
        <span class="mask bg-gradient-success opacity-8"></span>
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white">회원가입</h1>
              <p class="text-lead text-white">
                회원가입하고 매물보기 서비스를 누려보세요!
              </p>
            </b-col>
          </b-row>
        </div>
      </b-container>
      <div class="separator separator-bottom separator-skew zindex-100">
        <svg
          x="0"
          y="0"
          viewBox="0 0 2560 100"
          preserveAspectRatio="none"
          version="1.1"
          xmlns="http://www.w3.org/2000/svg"
        >
          <polygon
            class="fill-default"
            points="2560 0 2560 100 0 100"
          ></polygon>
        </svg>
      </div>
    </div>
    <!-- Page content -->
    <b-container class="mt--8 pb-5">
      <!-- Table -->
      <b-row class="justify-content-center">
        <b-col lg="6" md="8">
          <b-card no-body class="bg-secondary border-0">
            <b-card-body class="px-lg-5 py-lg-5 opacity-8 bordercolor">
              <div class="text-center text-muted mb-4">
                <small>아래의 정보를 입력해주세요</small>
              </div>
              <validation-observer
                v-slot="{ handleSubmit }"
                ref="formValidator"
              >
                <b-form role="form" @submit.prevent="handleSubmit(onSubmit)">
                  <base-input
                    alternative
                    class="mb-3"
                    prepend-icon="ni ni-circle-08"
                    placeholder="ID"
                    name="ID"
                    :state="false"
                    :rules="{ required: true }"
                    v-model="user.userId"
                  >
                  </base-input>

                  <base-input
                    alternative
                    class="mb-3"
                    prepend-icon="ni ni-hat-3"
                    placeholder="name"
                    name="name"
                    :rules="{ required: true }"
                    v-model="user.userName"
                  >
                  </base-input>

                  <base-input
                    alternative
                    class="mb-3"
                    prepend-icon="ni ni-email-83"
                    placeholder="Email"
                    name="Email"
                    :state="true"
                    :rules="{ required: true, email: true }"
                    v-model="user.userEmail"
                  >
                  </base-input>
                  <div>
                    <base-input
                      alternative
                      class="mb-3"
                      prepend-icon="ni ni-lock-circle-open"
                      placeholder="password"
                      type="password"
                      name="password"
                      :rules="{ required: true }"
                      v-model="user.userPassword"
                    >
                    </base-input>
                  </div>
                  <base-input
                    alternative
                    class="mb-3"
                    prepend-icon="ni ni-lock-circle-open"
                    placeholder="password check"
                    type="password"
                    name="password check"
                    :rules="{ required: true }"
                    v-model="userPassword2"
                  >
                  </base-input>

                  <base-input
                    alternative
                    class="mb-3"
                    prepend-icon="ni ni-mobile-button"
                    placeholder="phone"
                    name="phone"
                    :rules="{ required: true }"
                    v-model="user.userPhone"
                  >
                  </base-input>

                  <div class="text-muted font-italic">
                    <small
                      >password strength:
                      <span class="text-success font-weight-700" v-if="isStrong"
                        >strong</span
                      >
                      <span class="text-fail font-weight-700" v-if="isWeak"
                        >weakness</span
                      >
                    </small>
                  </div>
                  <b-row class=" my-4">
                    <b-col cols="12">
                      <!-- <base-input
                        :rules="{ required: { allowFalse: false } }"
                        name="Privacy"
                        Policy
                      >
                        <b-form-checkbox v-model="agree">
                          <span class="text-muted"
                            >I agree with the
                            <a href="#!">Privacy Policy</a></span
                          >
                        </b-form-checkbox>
                      </base-input> -->
                    </b-col>
                  </b-row>
                  <div class="text-center">
                    <b-button variant="primary" class="mt-4" @click="register"
                      >회원가입</b-button
                    >
                  </div>
                </b-form>
              </validation-observer>
            </b-card-body>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import axios from "axios";

const SERVER_URL = "http://localhost:8080/";

export default {
  name: "register",
  data() {
    return {
      user: {
        userId: "",
        userName: "",
        userEmail: "",
        userPhone: "",
        userPassword: ""
      },
      userPassword2: "",
      agree: false,
      chk: false,
      isStrong: false,
      isWeak: false
    };
  },
  methods: {
    strongChk: function(e) {
      console.log("asdf");
      if (
        patternEngAtListOne.test(this.user.userPassword) &&
        patternSpeAtListOne.test(this.user.userPassword) &&
        patternNumAtListOne.test(this.user.userPassword) &&
        this.user.userPassword >= 10
      ) {
        this.isWeak = false;
        this.isStrong = true;
      } else {
        this.isWeak = true;
        this.isStrong = false;
      }
    },
    register: function() {
      if (this.user.userId.length < 3) {
        this.$alertify.error("ID는 세 글자 이상이어야 합니다");
        return;
      }

      if (this.user.userName.length < 1) {
        this.$alertify.error("이름은 한 글자 이상이어야 합니다");
        return;
      }

      let regexp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
      if (!regexp.test(this.user.userEmail)) {
        this.$alertify.error("이메일은 이메일 형식으로 입력해주세요.");
        return;
      }

      let patternEngAtListOne = new RegExp(/[a-zA-Z]+/); // + for at least one
      let patternSpeAtListOne = new RegExp(/[~!@#$%^&*()_+|<>?:{}]+/); // + for at least one
      let patternNumAtListOne = new RegExp(/[0-9]+/); // + for at least one
      if (
        !patternEngAtListOne.test(this.user.userPassword) ||
        !patternSpeAtListOne.test(this.user.userPassword) ||
        !patternNumAtListOne.test(this.user.userPassword) ||
        this.user.userPassword.length < 8
      ) {
        this.$alertify.error(
          "비밀번호는 1개 이상의 특수문자, 대소문자 및 숫자를 포함하고 8자리 이상이여야 합니다."
        );
        return;
      }

      if (this.user.userPassword != this.userPassword2) {
        this.$alertify.error("비밀번호가 다릅니다.");
        return;
      }

      regexp = /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})([0-9]{3,4})([0-9]{4})$/;
      if (!regexp.test(this.user.userPhone)) {
        this.$alertify.error(
          "핸드폰 번호는 10자리 또는 11자리의 숫자로만 입력하세요."
        );
        return;
      }

      // this.validateUserId();
      // this.validateUserName();
      // this.validatePassword();
      // this.validatePassword2();
      // this.validatePhone();
      // this.validateEmail();
      axios
        .post(`${SERVER_URL}user/confirm/register`, this.user)
        .then(response => {
          console.log(response.data);
          if (response.data == "SUCCESS") {
            this.$alertify.success("회원가입 성공! 메인 화면으로 이동합니다.");
            this.$router.replace("/");
          } else {
            this.$alertify.success("중복되는 정보가 있어 가입에 실패했습니다.");
          }
        })
        .catch(() => {
          this.$alertify.error("회원가입에 실패했습니다.");
        });
    }
  }
};
</script>
<style scope>
.bordercolor {
  border: 1px solid rgb(18, 0, 66);
  border-radius: 5px;
  /* color:silver */
  /* background-color:oldlace */
  /* background-color:mediumaquamarine */
}
</style>
