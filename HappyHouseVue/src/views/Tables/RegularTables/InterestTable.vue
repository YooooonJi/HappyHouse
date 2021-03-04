<template>
  <b-card no-body style="height: 110%; ">
    <b-card-header class="border-0">
      <h3 class="mb-0">관심지역 목록</h3>
    </b-card-header>

    <div v-if="chk">
      <b-table
        align="center"
        hover
        :items="interestItems"
        :fields="fields"
        @row-clicked="showDetail"
        @row-dblclicked="deleteInterest"
        style="cursor: pointer;"
      >
        <template v-slot:cell(isdelete)="row">
          <b-form-group>
            <input type="checkbox" v-model="row.item.isdelete" />
          </b-form-group>
        </template>
      </b-table>

      <div align="right" style="margin-top: 3%; margin-right: 3%;">
        <base-button outline type="default" @click="deleteInterest">
          <span class="btn-inner--icon"><i class="ni ni-fat-remove"></i></span>
          <span class="btn-inner--text">삭제</span>
        </base-button>
      </div>
    </div>
    <div v-else align="center">
      <br />
      <br />

      <h1>관심지역을 등록해보세요!</h1>

      <br />
      <br />
    </div>
    <!-- <base-button type="primary" block class="mb-3">Default</base-button> -->
    <!-- modal -->
    <modal :show.sync="classic">
      <h6 slot="header" class="modal-title">{{ detail.interestName }}</h6>

      <ul align="left">
        <li>이름 : {{ detail.interestName }}</li>
        <li>주소 : {{ detail.interestAddress }}</li>
        <li>층수 : {{ detail.interestFloor }}층</li>
        <li>전용면적 : {{ detail.interestArea }}㎡</li>
        <li>거래금액 : {{ detail.interestDealAmount }} 원</li>
        <li>월세 : {{ detail.interestMonthlyRent }} 원</li>
        <li>보증금액 : {{ detail.interestDeposit }} 원</li>
        <!-- <li>{{ apt.거래금액 }}</li> -->
        <li>건축년도 : {{ detail.interestBuildYear }}</li>
      </ul>

      <template slot="footer">
        <base-button type="primary" class="ml-auto" @click="classic = false"
          >Close</base-button
        >
      </template>
    </modal>
  </b-card>
</template>
<script>
// import projects from "./../projects";
import { Table, TableColumn } from "element-ui";
import axios from "axios";
const SERVER_URL = "http://localhost:8080/";
export default {
  name: "InterestTable",
  components: {
    // [Table.name]: Table,
    // [TableColumn.name]: TableColumn
  },
  data() {
    return {
      fields: [],
      interestItems: [],
      classic: false,
      detail: {
        interestName: "",
        interestAddress: "",
        interestFloor: 0,
        interestArea: 0,
        interestDeposit: "",
        interestMonthlyRent: "",
        interestDealAmount: "",
        interestBuildYear: 0
      },
      list: [],
      chk: false
    };
  },
  created() {
    axios
      .get(`${SERVER_URL}interest/list/` + this.$store.getters.getUserId, {
        headers: {
          "auth-token": localStorage.getItem("token")
        }
      })
      .then(response => {
        var items = response.data.list;
        this.list = response.data.list;
        if (this.list.length > 0) this.chk = true;
        for (let i = 0; i < items.length; i++) {
          var item = {
            interestSeq: items[i].interestSeq,
            name: items[i].interestName,
            address: items[i].interestAddress,
            area: items[i].interestArea,
            dealAmount:
              items[i].interestDealAmount != null
                ? items[i].interestDealAmount
                : "0",
            monthlyRent:
              items[i].interestDealAmount == null
                ? items[i].interestMonthlyRent
                : "0",
            deposit:
              items[i].interestDealAmount == null
                ? items[i].interestDeposit
                : "0",
            isdelete: false
          };
          this.interestItems.push(item);
          this.fields = [
            {
              key: "name",
              label: "이름",
              sortable: true
            },
            {
              key: "address",
              label: "주소",
              sortable: false
            },
            {
              key: "area",
              label: "전용면적",
              sortable: false
            },
            {
              key: "dealAmount",
              label: "거래금액",
              sortable: false
            },
            {
              key: "monthlyRent",
              label: "월세금액",
              sortable: false
            },
            {
              key: "deposit",
              label: "보증금액",
              sortable: false
            },
            {
              key: "isdelete",
              label: "선택",
              sortable: false
            }
          ];
        }
      })
      .catch(e => {
        console.log(e);
        this.$store.dispatch("LOGOUT").then(() => this.$router.replace("/"));
      });
  },
  methods: {
    showDetail: function(record, index) {
      console.log(record.interestSeq);
      for (let i = 0; i < this.list.length; i++) {
        const element = this.list[i];
        if (element.interestSeq == record.interestSeq) {
          console.log(element);
          this.detail = element;
          if (this.detail.interestDealAmount == null) {
            this.detail.interestDealAmount = "0";
          } else {
            this.detail.interestMonthlyRent = "0";
            this.detail.interestDeposit = "0";
          }
          console.log(this.detail);
        }
      }
      this.classic = true;
    },
    deleteInterest: function() {
      for (let i = 0; i < this.interestItems.length; i++) {
        const element = this.interestItems[i];
        console.log(this.interestItems);
        if (element.isdelete) {
          axios
            .delete(`${SERVER_URL}interest/delete/` + element.interestSeq, {
              headers: {
                "auth-token": localStorage.getItem("token")
              }
            })
            .then(response => {
              if (response.data.result == "FAIL") {
                this.$alertify.error("삭제에 실패했습니다.");
                return;
              }
            })
            .catch(() => {
              this.$alertify.error(
                "삭제에 실패했습니다. 로그아웃 후 메인화면으로 돌아갑니다."
              );
              this.$store
                .dispatch("LOGOUT")
                .then(() => this.$router.replace("/"));
            });
        }
      }
      this.$alertify.success("삭제를 완료했습니다. 메인페이지로 이동합니다.");
      this.$router.replace("/");
      // this.reload();
    },
    reload: function() {
      this.interestItems = [];
      axios
        .get(`${SERVER_URL}interest/list/` + this.$store.getters.getUserId, {
          headers: {
            "auth-token": localStorage.getItem("token")
          }
        })
        .then(response => {
          var items = response.data.list;
          this.list = response.data.list;
          if (this.list.length > 0) this.chk = true;
          for (let i = 0; i < items.length; i++) {
            var item = {
              interestSeq: items[i].interestSeq,
              name: items[i].interestName,
              address: items[i].interestAddress,
              area: items[i].interestArea,
              dealAmount:
                items[i].interestDealAmount != null
                  ? items[i].interestDealAmount
                  : "0",
              monthlyRent:
                items[i].interestDealAmount == null
                  ? items[i].interestMonthlyRent
                  : "0",
              deposit:
                items[i].interestDealAmount == null
                  ? items[i].interestDeposit
                  : "0",
              isdelete: false
            };
            this.interestItems.push(item);
          }
          this.$alertify.success("삭제에 성공했습니다.");
        })
        .catch(e => {
          console.log(e);
          this.$store.dispatch("LOGOUT").then(() => this.$router.replace("/"));
        });
    }
  }
};
</script>
