<template>
  <div>
    <rooms-hero @search="searchRooms"/>
    <div class="container">
      <filtering-panel
        @change-selected-status="changeSelectedStatus"
      />
      <rooms-list :rooms="rooms" />
      <infinite-scroll 
        ref="scroll"
        @load-next-page="loadNextPage"
      />
      <scroll-top/>
    </div>
  </div>
</template>

<script>
import RoomsHero from '@/rooms/panel/RoomsHero';
import FilteringPanel from '@/rooms/panel/FilteringPanel';
import RoomsList from '@/rooms/panel/RoomsList';
import InfiniteScroll from '@/common/InfiniteScroll';
import { FETCH_ROOMS } from '@/store/actions.type';
import { CLEAR_ROOMS } from '@/store/mutations.type';
import ScrollTop from '@/common/ScrollTop';

const PAGE_SIZE = 15;

export default {
  name: 'RoomsPanel',
  components: {
    ScrollTop,
    RoomsHero,
    RoomsList,
    FilteringPanel,
    InfiniteScroll
  },
  props: {
    organisedOnly: {
      default: false,
      type: Boolean
    }
  },
  data: () => ({
    searchQuery: null,
    selectedStatus: null,
    currentPage: 0,
  }),
  computed: {
    rooms() {
      return this.$store.getters.rooms;
    }
  },
  beforeDestroy() {
    this.clearRooms();
  },
  methods: {
    searchRooms(query = null) {
      this.searchQuery = query;
      this.clearRooms();
    },

    changeSelectedStatus(newStatus) {
      this.selectedStatus = newStatus;
      this.clearRooms();
    },

    clearRooms() {
      this.currentPage = 0;
      this.$refs.scroll.reset();
      this.$store.commit(CLEAR_ROOMS);
    },

    loadNextPage($loadingState) {
      this.$store.dispatch(FETCH_ROOMS, {
        query: this.searchQuery,
        status: this.selectedStatus,
        page: this.currentPage,
        size: PAGE_SIZE
      })
        .then((data) => {
          if(data.content.length > 0) {
            $loadingState.loaded();
          }
          if(data.last) {
            $loadingState.complete();
          }
        })
        .catch(() => $loadingState.complete());
      this.currentPage++;
    }
  }
}
</script>

<style scoped>
</style>
