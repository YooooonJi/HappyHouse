<template>
  <div>
    <base-header
      class="header pb-8 pt-5 pt-lg-8 d-flex align-items-center profile-header"
      style="min-height: 400px; background-image: url(img/theme/profile-cover.jpg); background-size: cover; background-position: center top;"
    >
      <span class="mask bg-gradient-success opacity-8"></span>
      <!-- Card stats -->
      <b-row>
        <b-col xl="3" md="6">
          <div @click="clickAptTrade">
            <stats-card
              :title="aptTrade"
              type="gradient-red"
              :sub-title="String(aptTradeTotal)"
              icon="ni ni-building"
              class="mb-4"
              style="cursor: pointer;"
            >
            </stats-card>
          </div>
        </b-col>

        <b-col xl="3" md="6">
          <div @click="clickAptRent">
            <stats-card
              :title="aptRent"
              type="gradient-orange"
              :sub-title="String(aptRentTotal)"
              icon="ni ni-building"
              class="mb-4"
              style="cursor: pointer;"
            >
            </stats-card>
          </div>
        </b-col>
        <b-col xl="3" md="6">
          <div @click="clickHouseTrade">
            <stats-card
              :title="houseTrade"
              type="gradient-green"
              :sub-title="String(houseTradeTotal)"
              icon="ni ni-building"
              class="mb-4"
              style="cursor: pointer;"
            >
            </stats-card>
          </div>
        </b-col>
        <b-col xl="3" md="6">
          <div @click="clickHouseRent">
            <stats-card
              :title="houseRent"
              type="gradient-info"
              :sub-title="String(houseRentTotal)"
              icon="ni ni-building"
              class="mb-4"
              style="cursor: pointer;"
            >
            </stats-card>
          </div>
        </b-col>
      </b-row>
    </base-header>
    <b-container fluid class="mt--6">
      <br />
      <br />

      <search-bar @send-dong-code="sendDongCode" />
      <div>
        <h1 style="text-align:center;">{{ title }}</h1>
        <br />
        <b-row class="h-10">
          <b-col cols="1"></b-col>
          <b-col cols="5" align="left">
            <div style="overflow:hidden; width:100%; height:600px;">
              <apt-trade-list
                :aptlist="aptsTrade"
                @select-apt="selectApt"
                v-if="isAptTrade"
              />
              <apt-rent-list
                :aptlist="aptsRent"
                @select-apt="selectApt"
                v-if="isAptRent"
              />
              <house-trade-list
                :aptlist="housesTrade"
                @select-apt="selectApt"
                v-if="isHouseTrade"
              />
              <house-rent-list
                :aptlist="housesRent"
                @select-apt="selectApt"
                v-if="isHouseRent"
              />
            </div>
          </b-col>
          <b-col cols="5">
            <house-map :apt="selectedApt"></house-map>
            <apt-trade-detail :apt="selectedApt" v-if="isAptTrade" />
            <apt-rent-detail :apt="selectedApt" v-if="isAptRent" />
            <house-trade-detail :apt="selectedApt" v-if="isHouseTrade" />
            <house-rent-detail :apt="selectedApt" v-if="isHouseRent" />
          </b-col>
          <b-col cols="1"></b-col>
        </b-row>
      </div>
      <br />
      <br />
    </b-container>
  </div>
</template>
<script>
import SearchBar from "../components/Apt/SearchBar.vue";
import AptTradeList from "../components/Apt/AptTradeList.vue";
import AptTradeDetail from "../components/Apt/AptTradeDetail.vue";
import AptRentList from "../components/Apt/AptRentList.vue";
import AptRentDetail from "../components/Apt/AptRentDetail.vue";
import HouseTradeList from "../components/Apt/HouseTradeList.vue";
import HouseTradeDetail from "../components/Apt/HouseTradeDetail.vue";
import HouseRentList from "../components/Apt/HouseRentList.vue";
import HouseRentDetail from "../components/Apt/HouseRentDetail.vue";
import HouseMap from "../components/Apt/HouseMap.vue";
import axios from "axios";

let today = new Date();

export default {
  name: "Apt",
  components: {
    SearchBar,
    AptTradeList,
    AptTradeDetail,
    AptRentList,
    AptRentDetail,
    HouseTradeList,
    HouseTradeDetail,
    HouseRentList,
    HouseRentDetail,
    HouseMap
  },
  data() {
    return {
      dongCode: "",
      selectedApt: "",
      aptsTrade: [],
      aptsRent: [],
      housesTrade: [],
      housesRent: [],
      currentYear: today.getFullYear(),
      currentMonth: (today.getMonth() + 1) % 13,
      aptTradeTotal: 0,
      aptRentTotal: 0,
      houseTradeTotal: 0,
      houseRentTotal: 0,
      isAptTrade: true,
      isAptRent: false,
      isHouseTrade: false,
      isHouseRent: false,
      title: "아파트 매매"
    };
  },
  computed: {
    aptTrade: function() {
      return this.currentYear + "년 " + this.currentMonth + "월 아파트 매매";
    },
    aptRent: function() {
      return this.currentYear + "년 " + this.currentMonth + "월 아파트 전월세";
    },
    houseTrade: function() {
      return (
        this.currentYear + "년 " + this.currentMonth + "월 연립다세대 매매"
      );
    },
    houseRent: function() {
      return (
        this.currentYear + "년 " + this.currentMonth + "월 연립다세대 전월세"
      );
    }
  },
  methods: {
    sendDongCode: function(dongCode) {
      console.log(">>>>>>>>" + dongCode);
      this.dongCode = dongCode;
      console.log(process.env.VUE_APP_APT_DEAL_API_KEY);
      const API_KEY =
        "gB5J4C9%2BIuWxFuejCPHGSN%2B6dcqqTeqaRnYYfTjKDycfEU8KQcMFF8GRckOH5rUqr%2Bgc1S%2F0g%2FqS49Cm89yRiA%3D%3D";
      const API_URL_APT_TRADE =
        "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";
      const API_URL_APT_RENT =
        "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent";
      const API_URL_HOUSE_TRADE =
        "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcRHTrade";
      const API_URL_HOUSE_RENT =
        "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcRHRent";

      // 아파트 매매
      var params = {
        numOfRows: 1000,
        LAWD_CD: this.dongCode,
        DEAL_YMD:
          String(this.currentYear) +
          (this.currentMonth < 10
            ? "0" + String(this.currentMonth)
            : String(this.currentMonth)),
        serviceKey: decodeURIComponent(API_KEY) // 파라미터로 보내면 인코딩을 해서 보냄 key는 이미 인코딩 된 상태이므로 decode해주어야함
      };
      axios
        .get(API_URL_APT_TRADE, {
          // params: params
          params
        })
        .then(response => {
          console.log("아파트매매");
          console.log(response);
          // console.log(response.data.response.body.items.item);
          // tmp.push(response.data.response.body.items.item);
          this.aptsTrade = response.data.response.body.items.item;
          this.aptTradeTotal = response.data.response.body.totalCount;
        })
        .catch(error => {
          console.log(error);
        });

      // 아파트 전월세
      params = {
        numOfRows: 1000,
        LAWD_CD: this.dongCode,
        DEAL_YMD:
          String(this.currentYear) +
          (this.currentMonth < 10
            ? "0" + String(this.currentMonth)
            : String(this.currentMonth)),
        serviceKey: decodeURIComponent(API_KEY) // 파라미터로 보내면 인코딩을 해서 보냄 key는 이미 인코딩 된 상태이므로 decode해주어야함
      };
      axios
        .get(API_URL_APT_RENT, {
          // params: params
          params
        })
        .then(response => {
          console.log("아파트전월세");
          console.log(response);
          this.aptsRent = response.data.response.body.items.item;
          this.aptRentTotal = response.data.response.body.totalCount;
        })
        .catch(error => {
          console.log(error);
        });

      // 연립다세대 매매
      params = {
        numOfRows: 1000,
        LAWD_CD: this.dongCode,
        DEAL_YMD:
          String(this.currentYear) +
          (this.currentMonth < 10
            ? "0" + String(this.currentMonth)
            : String(this.currentMonth)),
        serviceKey: decodeURIComponent(API_KEY) // 파라미터로 보내면 인코딩을 해서 보냄 key는 이미 인코딩 된 상태이므로 decode해주어야함
      };
      axios
        .get(API_URL_HOUSE_TRADE, {
          // params: params
          params
        })
        .then(response => {
          console.log("연립다세대매매");
          console.log(response);

          this.housesTrade = response.data.response.body.items.item;
          this.houseTradeTotal = response.data.response.body.totalCount;
        })
        .catch(error => {
          console.log(error);
        });

      // 연립다세대 전월세
      params = {
        numOfRows: 1000,
        LAWD_CD: this.dongCode,
        DEAL_YMD:
          String(this.currentYear) +
          (this.currentMonth < 10
            ? "0" + String(this.currentMonth)
            : String(this.currentMonth)),
        serviceKey: decodeURIComponent(API_KEY) // 파라미터로 보내면 인코딩을 해서 보냄 key는 이미 인코딩 된 상태이므로 decode해주어야함
      };
      axios
        .get(API_URL_HOUSE_RENT, {
          // params: params
          params
        })
        .then(response => {
          console.log("연립다세대전월세");
          console.log(response);
          this.housesRent = response.data.response.body.items.item;
          this.houseRentTotal = response.data.response.body.totalCount;
        })
        .catch(error => {
          console.log(error);
        });
    },
    selectApt: function(apt) {
      this.selectedApt = apt;
    },
    clickAptTrade: function() {
      this.isAptTrade = true;
      this.isAptRent = false;
      this.isHouseTrade = false;
      this.isHouseRent = false;
      this.title = "아파트 매매";
    },
    clickAptRent: function() {
      this.isAptTrade = false;
      this.isAptRent = true;
      this.isHouseTrade = false;
      this.isHouseRent = false;
      this.title = "아파트 전월세";
    },
    clickHouseTrade: function() {
      this.isAptTrade = false;
      this.isAptRent = false;
      this.isHouseTrade = true;
      this.isHouseRent = false;
      this.title = "연립다세대 매매";
    },
    clickHouseRent: function() {
      this.isAptTrade = false;
      this.isAptRent = false;
      this.isHouseTrade = false;
      this.isHouseRent = true;
      this.title = "연립다세대 전월세";
    }
  }
};
</script>
