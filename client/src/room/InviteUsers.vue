<template>
  <div>
    <b-dropdown
      ref="dropdown"
      :disabled="disabled"
      v-model="isOpen"
      @active-change="active => isOpen = active"
    >
      <a
        v-tippy
        slot="trigger"
        title="Invite users to this room"
        class="is-unselectable button button-invite"
      >
        Invite Friends
        <caret-down :is-open="isOpen"/>
      </a>

      <div class="modal-card">
        <div class="modal-card-head">
          <i class="icon fa fa-envelope"/>
          Invitations
        </div>
        <div class="modal-card-body">
          <b-field label="Invite your friends to this room">
            <b-autocomplete
              v-model="inputValue"
              :data="userNames"
              placeholder="Username or email..."
              icon="envelope"
              @select="onSelect"
              @input="fetchUsersByUsernameOrEmail"
              @keydown.native.enter="onSelect(inputValue)"
            >
              <template slot="empty">
                Send invitation on <strong>{{ inputValue }}</strong>
              </template>
            </b-autocomplete>
          </b-field>
          <p class="users-to-invite">
            Users to invite:
          </p>
          <p
            v-if="usersToInvite.length === 0"
            class="no-users"
          >
            There are no people to invite.
            Type user name or email in the field above and press enter to add him to the list.
          </p>
          <div class="tags">
            <span
              v-for="(user, index) in usersToInvite"
              :class="user.username ? 'is-primary' : 'is-info'"
              :key="index"
              class="tag is-medium"
              @click.middle="deleteEntryFromUsersToInvite(index)"
            >
              {{ user.username || user.email }}
              <button
                class="delete-tag delete is-small"
                @click="deleteEntryFromUsersToInvite(index)"
              />
            </span>
          </div>
        </div>
        <footer class="modal-card-foot">
          <a
            class="button is-unselectable"
            @click.prevent="clear"
          >
            Clear
          </a>
          <a
            :disabled="usersToInvite.length === 0"
            class="button is-info is-unselectable"
            @click.prevent="onSubmit"
          >
            Invite them all!
          </a>
        </footer>
      </div>
    </b-dropdown>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import * as _ from 'lodash';
import { FETCH_USERS, INVITE_USERS } from '../store/actions.type';
import CaretDown from '../common/CaretDown';


export default {
  inputValue: 'InviteUsers',
  components: {
    CaretDown

  },
  props: {
    roomId: {
      type: Number,
      required: true
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
  data: () => ({
    isOpen: false,
    inputValue: '',
    usersToInvite: [],
  }),
  computed: mapGetters(['userNames']),
  methods: {
    onSubmit() {
      this.$store.dispatch(INVITE_USERS, { roomId: this.roomId, invitations: this.usersToInvite })
        .then(() => this.$snackbar.open('Invitations have been sent'))
        .then(() => this.closeView());
    },

    onSelect(inputValue) {
      if(inputValue) {
        const registeredUser = _.find(
          this.$store.getters.users,
          this.isUsernameOrEmailEqualTo(inputValue)
        );

        if (registeredUser !== undefined) {
          this.addKnownUser(registeredUser);
        } else {
          this.addUnknownUser(inputValue);
        }

        this.usersToInvite = _.uniqWith(this.usersToInvite, _.isEqual);
        this.inputValue = '';
      }
    },

    isUsernameOrEmailEqualTo(usernameOrEmail) {
      const usernameOrEmailLowerCase = usernameOrEmail.toLowerCase();
      return user =>
        user.username.toLowerCase() === usernameOrEmailLowerCase
        || user.email.toLowerCase() === usernameOrEmailLowerCase;
    },

    fetchUsersByUsernameOrEmail: _.throttle(function(usernameOrEmail) {
      this.$store.dispatch(FETCH_USERS, { page: 0, size: 20, query: usernameOrEmail });
    }, 500),

    addKnownUser(user) {
      this.usersToInvite.push(user);
    },

    addUnknownUser(email) {
      if(this.validateEmail(email)) {
        this.usersToInvite.push({
          username: null,
          email
        });
      }
    },

    validateEmail(value) {
      const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(value);
    },

    deleteEntryFromUsersToInvite(index) {
      this.usersToInvite.splice(index, 1);
    },

    closeView() {
      this.$refs.dropdown.toggle();
      this.clear();
    },

    clear() {
      this.inputValue = '';
      this.usersToInvite = [];
    }
  }
}
</script>

<style scoped>
  .modal-card {
    overflow: visible;
    width: 420px;
    text-align: left;
  }

  .icon {
    margin-right: 15px;
  }

  .modal-card-body {
    overflow: visible;
  }

  .users-to-invite {
    margin: 40px 0 10px;
  }

  .no-users {
    color: #aaaaaa;
    font-size: 15px;
  }

  .button-invite {
    background-color: #eee;
    border-color: black;
  }
</style>