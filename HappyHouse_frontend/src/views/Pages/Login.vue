<template>
  <div>
    <!-- Header -->

    <!-- <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9"> -->
    <div
      class="header pb-8 pt-5 pt-lg-8 d-flex align-items-center profile-header"
      style="min-height: 400px; background-image: url(img/theme/profile-cover.jpg); background-size: cover; background-position: center top;"
    >
      <b-container>
        <span class="mask bg-gradient-success opacity-8"></span>
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white">로그인</h1>
              <p class="text-lead text-white">
                매물보기를 이용하시려면 로그인해주세요
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
      <b-row class="justify-content-center">
        <b-col lg="5" md="7">
          <b-card no-body class="bg-secondary border-0 mb-0">
            <b-card-body class="px-lg-5 py-lg-5 bordercolor">
              <div class="text-center text-muted mb-4">
                <small>아이디와 비밀번호를 입력하세요</small>
              </div>
              <!-- <validation-observer
                v-slot="{ handleSubmit }"
                ref="formValidator"
              > -->
              <b-form role="form">
                <base-input
                  alternative
                  class="mb-3"
                  name="ID"
                  :rules="{ required: true }"
                  prepend-icon="ni ni-circle-08"
                  placeholder="ID"
                  v-model="user.userId"
                >
                </base-input>

                <base-input
                  alternative
                  class="mb-3"
                  name="Password"
                  :rules="{ required: true }"
                  prepend-icon="ni ni-lock-circle-open"
                  type="password"
                  placeholder="Password"
                  v-model="user.userPassword"
                >
                </base-input>

                <!-- <b-form-checkbox v-model="model.rememberMe">Remember me</b-form-checkbox> -->
                <div class="text-center">
                  <base-button
                    type="primary"
                    native-type="submit"
                    class="my-4"
                    @click="login"
                    >로그인</base-button
                  >
                </div>
              </b-form>
              <!-- </validation-observer> -->
            </b-card-body>
          </b-card>
          <b-row class="mt-3">
            <b-col cols="6">
              <router-link to="/main" class="text-light"
                ><small>비밀번호를 잊으셨나요?</small></router-link
              >
            </b-col>
            <b-col cols="6" class="text-right">
              <router-link to="/register" class="text-light"
                ><small>회원가입 하기</small></router-link
              >
            </b-col>
          </b-row>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>
<script>
export default {
  data() {
    return {
      user: {
        userId: "",
        userPassword: ""
      }
    };
  },
  created() {
    if (localStorage.getItem("token") != null) this.$router.replace(`/`);
  },
  computed: {
    nextRoute() {
      return this.$route.params.nextRoute ? this.$route.params.nextRoute : "";
    }
  },
  methods: {
    login: function() {
      this.$store
        .dispatch("LOGIN", this.user)
        .then(() => {
          if (this.$store.getters.getUserId != undefined) {
            console.log(this.$store.getters.getUserId);
            this.$router.replace(`/${this.nextRoute}`);
          } else {
            this.$alertify.error("아이디 또는 비밀번호가 바르지 않습니다.");
          }
        })
        .catch(({ message }) => {
          // this.msg = message;
          this.$alertify.error("로그인에 문제가 생겼습니다.");
        });
    },
    onSubmit() {}
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
