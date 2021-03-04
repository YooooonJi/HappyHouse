<template>
  <div>
    <b-card no-body="no-body">
      <b-row align-v="center" slot="header">
        <h3 class="mb-0">정보게시판</h3>
      </b-row>

      <div>
        <b-modal hide-footer="hide-footer" v-model="classicModal">
          <template #modal-title>
            <h3>게시판 글 조회</h3>
          </template>
          <div class="d-block text-center">
            <h1>[{{ noticeitem.title }}]</h1>
            <br /><br />
            <span v-bind:style="{ fontSize: '15px' }">{{
              noticeitem.content
            }}</span>
          </div>
          <b-button
            class="mt-3"
            block="block"
            @click="deleteNotice(noticeitem.boardId)"
            >Delete</b-button
          >
          <b-button
            class="mt-3"
            block="block"
            @click="$bvModal.show('bv-modal-update')"
            >Update</b-button
          >
        </b-modal>

        <b-modal id="bv-modal-example" hide-footer="hide-footer">
          <template #modal-title>
            <h3>게시판 글 등록</h3>
          </template>
          <div class="d-block text-center">
            <b-form-group id="input-group-1" label="제목" label-for="input-1">
              <b-form-input
                id="input-1"
                v-model="title"
                type="email"
                required="required"
                placeholder="제목입력해주세요"
              ></b-form-input>
            </b-form-group>
            <b-form-textarea
              id="textarea-no-resize"
              v-model="content"
              placeholder="내용 입력해주세요"
              no-resize="no-resize"
            ></b-form-textarea>
          </div>
          <b-button class="mt-3" block="block" @click="insertNotice"
            >등록</b-button
          >
        </b-modal>

        <b-modal
          id="bv-modal-update"
          hide-footer="hide-footer"
          v-if="classicModal"
          @close="classicModalHide"
        >
          <template #modal-title>
            <h3>게시판 글 수정</h3>
          </template>
          <div class="d-block text-center">
            <b-form-group id="input-group-1" label="제목" label-for="input-1">
              <b-form-input
                id="input-1"
                v-model="updatetitle"
                type="email"
                required="required"
                placeholder="제목 수정해주세요"
              ></b-form-input>
            </b-form-group>
            <b-form-textarea
              id="textarea-no-resize"
              v-model="updatecontent"
              placeholder="내용 수정해주세요"
              no-resize="no-resize"
            ></b-form-textarea>
          </div>
          <b-button
            class="mt-3"
            block="block"
            @click="updateComplete(noticeitem.boardId)"
            >수정</b-button
          >
        </b-modal>
      </div>

      <div style="margin:auto;">
        <b-form
          class="navbar-search form-inline m-5"
          :class="
            $route.meta.searchClasses
              ? $route.meta.searchClasses
              : 'navbar-search-light'
          "
          id="navbar-search-main"
        >
          <b-form-group class="mb-0">
            <b-input-group class="input-group-alternative input-group-merge">
              <div class="input-group-prepend">
                <span class="input-group-text">
                  <i class="fas fa-search"></i>
                </span>
              </div>
              <b-form-input
                placeholder="제목을 검색해주세요"
                v-model="searchword"
                @keypress.enter="showList"
                style="cursor: pointer;"
              ></b-form-input>
            </b-input-group>
          </b-form-group>
        </b-form>
      </div>

      <div class="container" style="cursor: pointer;">
        <table class="table table-hover">
          <thead>
            <tr>
              <th class="tableRow">번호</th>
              <th class="tableRow">제목</th>
              <th class="tableRow">날짜</th>
            </tr>
          </thead>
          <tbody>
            <tr v-if="noticeitems == null">
              {{
                searchword
              }}에 대한 검색 결과가 없습니다
            </tr>
            <tr
              v-for="(noticeitem, index) in noticeitems"
              :key="index"
              @click="classicModalshow(noticeitem.boardId)"
            >
              <td>
                {{ index + 1 }}
              </td>
              <td>
                {{ noticeitem.title }}
              </td>
              <td>
                {{ getFormatDate(noticeitem.regDt) }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <base-button
        icon="icon"
        type="primary"
        target="_blank"
        @click="$bvModal.show('bv-modal-example')"
        style=""
      >
        글쓰기</base-button
      >
      <b-card-footer class="py-4 d-flex justify-content-end">
        <b-pagination
          v-model="curPageNum"
          :total-rows="totalPage"
          :per-page="dataPerPage"
          @input="moveListPage(curPageNum)"
        ></b-pagination>
      </b-card-footer>
    </b-card>
  </div>
</template>
<script>
import projects from "./../projects";
import { Table, TableColumn } from "element-ui";

import store from "@/store/store.js";
import { mapGetters } from "vuex";
import http from "@/util/http-common";
import axios from "axios";
import Vue from "vue";
import VueSimpleAlert from "vue-simple-alert";

Vue.use(VueSimpleAlert);

const exampleItems = [...Array(150).keys()].map(i => ({
  id: i + 1,
  name: "Item " + (i + 1)
}));

export default {
  name: "light-table",
  props: {
    header: {
      type: String,
      default: require("@/assets/apt.png")
    }
  },
  components: {
    // Modal, Pagination
  },
  data: function() {
    return {
      itemno: 0,
      // detail
      modalShow: false,
      classicModal: false,
      isDetail: true,
      // update
      updatecontent: "",
      updatetitle: "",
      // search
      searchword: "",
      // insert
      isInsert: false,
      title: "",
      content: "",
      projects,
      // pagination
      curPageNum: 1,
      dataPerPage: 6,
      totalPage: 0,

      noticeitems: [],
      noticeitem: {}
    };
  },
  computed: {
    ...mapGetters(["noticeitems"]),
    ...mapGetters(["noticeitem"]),
    headerStyle() {
      return { backgroundImage: `url(${this.header})` };
    }
  },

  created() {
    http
      .post(`/boardlist`, {
        offset: (this.curPageNum - 1) * this.dataPerPage,
        searchword: this.searchword,
        limit: this.dataPerPage
      })
      .then(({ data }) => {
        this.noticeitems = data.list;
        this.totalPage = data.count;
      })
      .catch(() => {
        this.$alert("에러가 발생했습니다.");
      });
  },
  methods: {
    onChangePage(pageOfItems) {
      this.pageOfItems = pageOfItems;
    },

    showList() {
      http
        .post(`/boardlist`, {
          offset: (this.curPageNum - 1) * this.dataPerPage,
          searchword: this.searchword,
          limit: this.dataPerPage
        })
        .then(({ data }) => {
          this.noticeitems = data.list;
          this.totalPage = data.count;
        })
        .catch(() => {
          this.$alert("에러가 발생했습니다.");
        });
    },
    moveListPage(curPageNum) {
      http
        .post(`/boardlist`, {
          offset: (this.curPageNum - 1) * this.dataPerPage,
          searchword: this.searchword,
          limit: this.dataPerPage
        })
        .then(({ data }) => {
          this.noticeitems = data.list;
          this.totalPage = data.count;
        })
        .catch(() => {
          this.$alert("에러가 발생했습니다.");
        });
    },
    loginneed() {
      this.$alert("로그인이 필요합니다");
      //this.$router.push('/');
    },
    getFormatDate(regDt) {
      var date = regDt[0] + "/" + regDt[1] + "/" + regDt[2];
      return date;
    },
    movePage() {
      this.$router.push("/");
    },

    // Detail
    classicModalshow(no) {
      this.itemno = no;
      http
        .get(`/boards/${no}`)
        .then(({ data }) => {
          this.noticeitem = data.dto;
        })
        .catch(() => {
          this.$alert("에러가 발생했습니다.");
        });
      this.classicModal = true;
    },
    classicModalHide() {
      this.classicModal = false;
      this.isDetail = true;
      this.isInsert = false;
    },

    // updateModal
    updateModal(title, content) {
      this.updatetitle = title;
      this.updatecontent = content;
      this.isDetail = false;
    },

    // update
    updateComplete(boardId) {
      http
        .post(`/boards/${boardId}`, {
          boardId: boardId,
          title: this.updatetitle,
          content: this.updatecontent
        })
        .then(() => {
          this.$alert("수정이 완료되었습니다.");
        })
        .catch(() => {
          this.$alert("삭제 처리시 에러가 발생했습니다.");
        })
        .finally(() => {
          this.classicModalHide();
          this.showList();
        });
    },

    blankCheck() {
      let err = true;
      let msg = "";
      !this.updatetitle &&
        ((msg = "제목을 입력해주세요"),
        (err = false),
        this.$alert(msg),
        this.$refs.updatetitle.focus());
      err &&
        !this.updatecontent &&
        ((msg = "내용을 입력해주세요"),
        this.$alert(msg),
        (err = false),
        this.$refs.updatecontent.focus());
    },

    // delte
    deleteNotice(no) {
      http
        .delete(`/boards/` + no)
        .then(() => {
          this.$alert("삭제가 완료되었습니다.");
        })
        .catch(() => {
          this.$alert("삭제 처리시 에러가 발생했습니다.");
        })
        .finally(() => {
          this.classicModalHide();
          this.showList();
        });
    },
    // insert
    insertOpen() {
      this.updatetitle = "";
      this.updatecontent = "";
      this.isInsert = true;
      this.isDetail = false;
    },
    insertNotice() {
      http
        .post(`/boards`, {
          title: this.title,
          content: this.content,
          userSeq: this.$store.getters.getUserSeq
        })
        .then(() => {
          this.$alert("등록이 완료되었습니다.");
        })
        .catch(() => {
          this.$alert("등록 처리시 에러가 발생했습니다.");
        })
        .finally(() => {
          this.classicModalHide();
          this.showList();
        });
    }
  }
};
</script>

<style lang="scss" scoped="scoped">
.md-card-actions.text-center {
  display: flex;
  justify-content: center !important;
}
.contact-form {
  margin-top: 30px;
}

.md-has-textarea + .md-layout {
  margin-top: 15px;
}
td {
  width: auto;
}
ul {
  justify-content: center;
}
</style>
