<template>
  <div class="is-fullwidth has-navbar">
    <div class="is-fullwidth room-header">
      <div class="container room-header-content">
        <div class="is-size-4 room-header-section">
          <i class="fa fa-calendar-o mr-1"></i> {{room.date}}
        </div>
        <div class="is-size-4 room-header-section ">
          <i class="fa fa-user mr-1"></i> {{room.organizer}}
        </div>
      </div>
    </div>
    <div class="container">
      <video class="video-placeholder is-fullwidth mt-1"></video>
    </div>
    <div class="container">
      <h2 class="is-size-1 mt-2">{{room.title}}</h2>
      <tags :tags="room.tags"></tags>
      <p class="has-text-grey-dark">{{room.description}}</p>
    </div>
  </div>
</template>

<script>
  import Tags from '@/room/Tags';

  export default {
    name: "RoomPanel",
    props: ['id'],
    components: {
      Tags,
    },
    computed: {
      room: function() {
        return {
          id: '123',
          organizer: 'Wojtek',
          description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla auctor eu quam ut fermentum. Mauris vel est porta justo rutrum tempor at in nibh. Aliquam ullamcorper elit sit amet mattis rutrum. Ut fermentum risus ut tincidunt pellentesque. Nam justo neque, maximus id quam sed, tincidunt dictum lacus. Vestibulum at velit felis. Nullam neque justo, tristique eu ultrices id, interdum quis nulla. Donec volutpat odio ut mollis convallis. Vestibulum ultricies urna velit, eget semper mauris iaculis at. Nam eget erat et ligula dapibus varius. In porta ut mauris ut condimentum. Mauris mollis aliquet felis, vel tempus leo dapibus tempus. Maecenas tincidunt, dolor a dictum congue, sem nunc pellentesque diam, vitae lacinia enim dolor eu arcu. Donec aliquam mi mi.\n' +
          '\n' +
          'Ut vel dolor ante. Donec finibus risus vel est malesuada, mollis fermentum lorem venenatis. Cras ut orci sit amet ante ultrices tempus quis et erat. Donec eget sapien porttitor, ornare nulla eget, tristique ligula. Donec facilisis tortor ex, vitae ultricies libero sodales id. Suspendisse quis mauris suscipit, viverra magna id, dictum lacus. Vivamus eu varius libero.',
          logoURL: 'someURL',
          title: 'Room title',
          date: '30.01.2020, 16:30',
          tags: ['a', 'b', 'c']
        }
      }
    }
  }
</script>

<style scoped>
  .room-header {
    display: flex;
    flex-direction: row;
    background-color: #eee;
    height: 50px;
  }
  .room-header-content {
    display: flex;
    flex-direction: row;
    align-items: center;
  }
  .room-header-section {
    width: 33%;
  }
  .has-navbar {
    padding-top: 52px;
  }
  .video-placeholder {
    background-color: darkkhaki;
  }
</style>