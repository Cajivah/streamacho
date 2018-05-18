<template>
  <div class="image field create-room-logo">
    <label class="load-picture file-label">
      <div class="control">
        <input
          class="file-input"
          type="file"
          name="logo"
          accept="image/*"
          @change="processFile"
        >
        <span
          v-if="image === null"
          class="content"
        >
          <p>
            <i class="icon fa fa-upload"/>
            <br/>
            Choose a logo
          </p>
        </span>
        <img
          :src="image"
          class="create-room-image"
        >
      </div>
    </label>
  </div>
</template>

<script>
export default {
  name: 'RoomImageDropzone',
  data: () => ({
    image: null,
  }),
  methods: {
    processFile(event) {
      this.image = URL.createObjectURL(event.target.files[0]);
    }
  }
}
</script>

<style scoped>
  .image {
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 50%;
    background-color: white;
    color: gray;
    width: 260px;
    height: 260px;
  }

  .load-picture {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    border-radius: 50%;
    border: dotted 1px gray;
    width: 252px;
    height: 252px;
    font-size: 20px;
  }

  .load-picture:hover {
    background-color: #f6f6f6;
  }

  .content {
    font-size: 30px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
  }
</style>
