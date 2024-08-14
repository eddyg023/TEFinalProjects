<script>
import playerService from '../services/PlayerService.js'

export default {
  data(){
    return {
      newPlayerName: '',
      players: [],
      showForm: false,
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
  created() {
    playerService.getPlayers()
      .then(response => {
        this.players = response.data;
      }).catch(error => {
        console.error(error);
      });
  },
  methods: {
    showPlayer(id){
      this.$router.push({name: 'PlayerDetail', params: {id: id}});
    },
    addPlayer(){
      const newPlayer = {
        name: this.newPlayerName,
        number: this.newPlayerNumber,
        player_position: this.newPlayerPosition,
        height: this.newPlayerHeight,
        weight: this.newPlayerWeight,
        age: this.newPlayerAge,
        ppg: this.newPlayerPPG,
        apg: this.newPlayerAPG,
        rpg: this.newPlayerRPG,
        spg: this.newPlayerSPG,
        bpg: this.newPlayerBPG,
        completed: false
      };

      playerService.addPlayer(newPlayer)
        .then(response => {
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
          this.loadPlayers();
          this.players.push(response.data);
          this.showForm = false;
        }).catch(error => {
          console.error(error);
        });
    },
    loadPlayers(){
      playerService.getPlayers()
        .then(response => {
          this.players = response.data;
        }).catch(error => {
          console.error(error);
        });
    },
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
    }
  }
}
</script>

<template>
  <div class="container">
    <header>
      <h1>Players</h1>
    </header>
    <section class="form-section" v-if="showForm && $store.state.token != null">
      <form @submit.prevent="addPlayer">
        <fieldset class="form-fieldset">
          <legend>Add New Player</legend>
          <label>
            <span>Player Name:</span>
            <input type="text" v-model="newPlayerName" placeholder="Enter player name" required>
            <span>Player Number:</span>
            <input type="number" v-model="newPlayerNumber" placeholder="Enter player number" required>
            <span>Player Position:</span>
            <select v-model="newPlayerPosition" required>
              <option value="Guard">Guard</option>
              <option value="Forward">Forward</option>
              <option value="Center">Center</option>
            </select>
            <span>Player Height (inches):</span>
            <input type="number" v-model="newPlayerHeight" placeholder="Enter player height" required>
            <span>Player Weight(lbs):</span>
            <input type="number" v-model="newPlayerWeight" placeholder="Enter player weight" required>
            <span>Player Age:</span>
            <input type="number" v-model="newPlayerAge" placeholder="Enter player age" required>
            <span>Points Per Game:</span>
            <input type="number" v-model="newPlayerPPG" placeholder="Enter player PPG" required>
            <span>Assists Per Game:</span>
            <input type="number" v-model="newPlayerAPG" placeholder="Enter player APG" required>
            <span>Rebounds Per Game:</span>
            <input type="number" v-model="newPlayerRPG" placeholder="Enter player RPG" required>
            <span>Steals Per Game:</span>
            <input type="number" v-model="newPlayerSPG" placeholder="Enter player SPG" required>
            <span>Blocks Per Game:</span>
            <input type="number" v-model="newPlayerBPG" placeholder="Enter player BPG" required>
          </label>
          <button type="submit" class="btn btn-primary">Add Player</button>
          <button type="button" class="btn btn-secondary" @click="cancelForm">Cancel</button>
        </fieldset>
      </form>
    </section>
    <button v-if="!showForm && $store.state.token != null" @click="showForm = true">Add New Player</button>
    <section class="player-list">
      <article v-for="player in players" :key="player.id" @click="showPlayer(player.playerId)">
        <h2>{{ player.name }}</h2>
        <p> #{{ player.number }}</p>
      </article>
    </section>
  </div>
</template>

<style scoped>
  header {
    background-color: #337ab7;
    padding: 20px;
    margin-bottom: 10px;
    border-bottom: 1px solid #ddd;
    border-radius: 10px 10px 0 0;
    text-align: center;
  }

  header h1 {
    color: #fff;
    font-size: 24px;
    margin: 0;
  }

  /* Form styles */
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

  /* Player list styles */
  .player-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    padding: 20px;
  }

  article {
    border: 1px solid #ccc;
    border-radius: 5px;
    padding: 10px;
    margin: 10px;
    cursor: pointer;
    width: calc(33.33% - 20px);
    text-align: center;
  }

  article:hover {
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }

  article h2 {
    font-size: 18px;
    margin: 10px;
  }

  article p {
    font-size: 16px;
    margin: 10px;
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