<template>
  <article v-if="player" class="player-profile">
    <header>
      <h1>{{ player.name }}</h1>
      <h2> #{{ player.number }}</h2>
    </header>
    <button v-if="!showForm && $store.state.token != null" @click="showForm = true" class="updateButton">Update Player</button>
    <button v-if="$store.state.token != null" @click="deletePlayer" class="deleteButton">Delete Player</button>
    <section>
      <section class="form-section" v-if="showForm && $store.state.token != null">
        <form @submit.prevent="updatePlayer">
          <fieldset class="form-fieldset">
            <legend>Update Player</legend>
            <button type="submit" class="btn btn-primary" @click="updatePlayer">Update Player</button>
            <button type="button" class="btn btn-secondary" @click="cancelForm">Cancel</button>
            <label>
              <span>Player Name:</span>
              <input type="text" v-model="newPlayerName" placeholder="Enter player name" >
              <span>Player Number:</span>
              <input type="number" v-model="newPlayerNumber" placeholder="Enter player number" >
              <span>Player Position:</span>
              <select v-model="newPlayerPosition" >
                <option value="Guard">Guard</option>
                <option value="Forward">Forward</option>
                <option value="Center">Center</option>
              </select>
              <span>Player Height (inches):</span>
              <input type="number" v-model="newPlayerHeight" placeholder="Enter player height" >
              <span>Player Weight (lbs):</span>
              <input type="number" v-model="newPlayerWeight" placeholder="Enter player weight" >
              <span>Player Age:</span>
              <input type="number" v-model="newPlayerAge" placeholder="Enter player age" >
              <span>Points Per Game:</span>
              <input type="number" v-model="newPlayerPPG" placeholder="Enter player PPG" >
              <span>Assists Per Game:</span>
              <input type="number" v-model="newPlayerAPG" placeholder="Enter player APG" >
              <span>Rebounds Per Game:</span>
              <input type="number" v-model="newPlayerRPG" placeholder="Enter player RPG" >
              <span>Steals Per Game:</span>
              <input type="number" v-model="newPlayerSPG" placeholder="Enter player SPG" >
              <span>Blocks Per Game:</span>
              <input type="number" v-model="newPlayerBPG" placeholder="Enter player BPG" >
            </label>
            <button type="submit" class="btn btn-primary" @click="updatePlayer">Update Player</button>
            <button type="button" class="btn btn-secondary" @click="cancelForm">Cancel</button>
          </fieldset>
        </form>
      </section>
      <img src="/images/profileheader.jpg" class="profilepicture"/>
      <ul>
        <li><span>Position:</span> {{ player.player_position }}</li>
        <li><span>Height (inches):</span> {{ player.height }}</li>
        <li><span>Weight (lbs):</span> {{ player.weight }}</li>
        <li><span>Age:</span> {{ player.age }}</li>
        <li><span>Points Per Game:</span> {{ player.points_per_game }}</li>
        <li><span>Assists Per Game:</span> {{ player.assists_per_game }}</li>
        <li><span>Rebounds Per Game:</span> {{ player.rebounds_per_game }}</li>
        <li><span>Steals Per Game:</span> {{ player.steals_per_game }}</li>
        <li><span>Blocks Per Game:</span> {{ player.blocks_per_game }}</li>
      </ul>

    </section>
    <button @click="$router.go(-1)">Back to Previous</button>
  </article>
</template>

<script>
import playerService from '@/services/PlayerService'
import teamService from '@/services/TeamService'


export default {
  data(){
    return {
      player: null,
      team: [],
      showForm: false,
      newPlayerName: '',
      newPlayerNumber: '',
      newPlayerPosition: '',
      newPlayerHeight: '',
      newPlayerWeight: '',
      newPlayerAge: '',
      newPlayerPPG: '',
      newPlayerAPG: '',
      newPlayerRPG: '',
      newPlayerSPG: '',
      newPlayerBPG: ''
    };
  },
  created(){
    playerService.getPlayer(this.$route.params.id)
      .then(response => {
        this.player = response.data;
      }).catch(error => {
        console.error(error);
      });
      teamService.getTeam(this.$route.params.id)
      .then(response => {
        this.team = response.data;
      }).catch(error => {
        console.error(error);
      });
  },
  methods: {
    cancelForm() {
      this.showForm = false;
      this.newPlayerName = '';
      this.newPlayerNumber = '';
      this.newPlayerPosition = '';
      this.newPlayerHeight = '';
      this.newPlayerWeight = '';
      this.newPlayerAge = '';
      this.newPlayerPPG = '';
      this.newPlayerAPG = '';
      this.newPlayerRPG = '';
      this.newPlayerSPG = '';
      this.newPlayerBPG = '';
    },
    updatePlayer() {
      const updatedPlayer = {
        name: this.newPlayerName,
        number: this.newPlayerNumber,
        player_position: this.newPlayerPosition,
        height: this.newPlayerHeight,
        weight: this.newPlayerWeight,
        age: this.newPlayerAge,
        points_per_game: this.newPlayerPPG,
        assists_per_game: this.newPlayerAPG,
        rebounds_per_game: this.newPlayerRPG,
        steals_per_game: this.newPlayerSPG,
        blocks_per_game: this.newPlayerBPG
      };

      playerService.updatePlayer(this.$route.params.id, updatedPlayer)
        .then(response => {
          this.player = response.data;
          this.showForm = false;
        }).catch(error => {
          console.error(error);
        });
    },
    deletePlayer() {
      playerService.deletePlayer(this.$route.params.id)
        .then(response => {
          this.$router.push({name: 'Player'});
        }).catch(error => {
          console.error(error);
        });
    }
  }
}
</script>

<style scoped>
  .player-profile {
    max-width: 600px;
    margin: 40px auto;
    padding: 20px;
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    border-radius: 10px;
  }

  header {
    background-color: #337ab7;
    color: #fff;
    padding: 10px;
    border-radius: 10px 10px 0 0;
  }

  .profilepicture {
    width: 200px;
    height: 300px;
    object-fit: cover;
    border-radius: 100px;
    display: block;
    margin: 0 auto;
    margin-top: 20px;
  }

  h1, h2 {
    text-align: center;
    font-size: 24px;
    margin-bottom: 10px;
  }

  section {
    padding: 20px;
  }

  ul {
    list-style: none;
    padding: 0;
    margin: 0;
  }

  li {
    margin-bottom: 10px;
  }

  span {
    font-weight: bold;
    margin-right: 10px;
  }

  button{
    background-color: #337ab7;
    color: #fff;
    font-size: small;
    padding: 5px 10px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }

  .form-section {
    padding: 20px;
  }
  
  .form-fieldset legend {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 10px;
  }

  label {
    display: block;
    margin-bottom: 10px;
  }

  label span {
    display: block;
    margin-bottom: 5px;
  }

  input[type="text"], input[type="number"], select {
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 5px;
    width: 100%;
    margin-bottom: 10px;
  }

  button[type="submit"] {
    background-color: #337ab7;
    color: #fff;
    padding: 10px 20px;
    margin: 10px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }

  button:hover {
    background-color: #23527c;
  }

  button{
    background-color: #337ab7;
    color: #fff;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }

  .updateButton{
    margin-top: 10px;
    margin-right: 5px;
  }

  @media (max-width: 1024px) {
    nav {
      text-align: center;
    }

    .form-fieldset {
      flex-direction: column;
    }

    label {
      width: 100%;
    }

    input[type="text"], input[type="number"], select {
      width: 100%;
    }

    .player-list {
      flex-direction: column;
    }

    article {
      width: 100%;
      margin: 10px 0;
    }
  }

  @media (max-width: 425px) {
    nav {
      text-align: center;
    }
    .form-fieldset {
      padding: 10px;
    }

    label {
      margin-bottom: 5px;
    }

    input[type="text"], input[type="number"], select {
      padding: 5px;
      font-size: 14px;
    }

    button.btn.btn-primary {
      width: auto;
      padding: 10px 20px;
      
    }

    button[type="submit"] {
      padding: 5px 10px;
      font-size: 14px;
    }

    .player-list {
      padding: 10px;
    }

    article {
      padding: 5px;
      margin: 5px 0;
    }

    article h2 {
      font-size: 16px;
    }

    article p {
      font-size: 14px;
    }
  }
</style>