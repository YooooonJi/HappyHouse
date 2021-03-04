<template>
  <div>
    <div v-if="apt">
      <h3>{{ apt.아파트 }}</h3>
      <!-- <div style="margin: 10px"></div> -->
      <ul align="left">
        <li>아파트 이름 : {{ apt.아파트 }}</li>
        <li>주소 : {{ apt.법정동 }} {{ apt.지번 }}</li>
        <li>층수 : {{ apt.층 }}층</li>
        <li>전용면적 : {{ apt.전용면적 }}㎡</li>
        <li>
          거래금액 : {{ (apt.거래금액.replace(",", "") * 10000) | price }}원
        </li>
        <!-- <li>{{ apt.거래금액 }}</li> -->
        <li>건축년도 : {{ apt.건축년도 }}</li>
      </ul>
      <ul align="right" v-if="$store.getters.getAccessToken">
        <base-button icon type="success" @click="registerInterest">
          <span class="btn-inner--icon"
            ><i class="ni ni-favourite-28"></i
          ></span>
          <span class="btn-inner--text">관심지역 설정</span>
        </base-button>
      </ul>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import axios from "axios";

export default {
  name: "AptTradeDetail",
  props: {
    apt: [Object, String]
  },
  computed: {
    ...mapGetters(["getAccessToken"])
  },
  filters: {
    price: function(value) {
      if (!value) return value;
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
  },
  methods: {
    registerInterest: function() {
      const SERVER_URL = "http://localhost:8080/";
      var interest = {
        userId: this.$store.getters.getUserId,
        interestName: this.apt.아파트,
        interestAddress: this.apt.법정동 + " " + this.apt.지번,
        interestFloor: this.apt.층,
        interestArea: this.apt.전용면적,
        interestDealAmount: (this.apt.거래금액.replace(",", "") * 10000)
          .toString()
          .replace(/\B(?=(\d{3})+(?!\d))/g, ","),
        interestBuildYear: this.apt.건축년도
      };

      axios
        .post(`${SERVER_URL}interest/register`, interest, {
          headers: {
            "auth-token": this.$store.getters.getAccessToken
          }
        })
        .then(response => {
          console.log(response.data);
          if (response.data == "SUCCESS") {
            this.$alertify.success("관심지역에 추가되었습니다.");
          } else {
            this.$alertify.error("관심지역은 10개 까지만 추가할 수 있습니다.");
          }
        })
        .catch(() => {
          this.$store.dispatch("LOGOUT").then(() => this.$router.replace("/"));
        });
    }
  }
};
</script>

<style></style>
