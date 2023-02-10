import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import Paginacija from './Paginacija';

class MojiPredmeti extends React.Component {
  constructor(){
    super();
    this.state = {
      data: [],
      trenutnaStrana:1,
    }
    this.setTrenutnaStranica = this.setTrenutnaStranica.bind(this)
    }
    state = {
      
    }
    
    indeksPoslednjegPredmeta = 4;
    indeksPrvogPredmeta = 1;
    trenutniPredmeti = [];
    
    elementiPoStrani= 4;

    setTrenutnaStranica(brojStranice = 1) {
        console.log("HELLO WORLD")
        console.log("BROJ STRANICE JE " + brojStranice)
        this.state.trenutnaStrana = brojStranice;
        console.log("TRENUTNA JE " + this.state.trenutnaStrana)
        this.forceUpdate();
    }

    componentDidMount() {
      axios({
          method: 'get',
          url: '/api/v1/predmeti/slusa',
          baseURL: 'http://localhost:8080',
          data: {},
          headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
        }).then((response) => {
          console.log(response);
          this.setState({data: response.data})
          console.log("DATA SET " + this.state.data);
          //this.setState({trenutnaStrana : 1})
          //console.log("TRENUTNA STRANA SET" + this.state.trenutnaStrana)
          
        }, (error) => {
          console.log(error);
        });
        
    }
      
    render(){
      
      this.indeksPoslednjegPredmeta = this.state.trenutnaStrana * this.elementiPoStrani;
      this.indeksPrvogPredmeta = this.indeksPoslednjegPredmeta - this.elementiPoStrani;
      this.trenutniPredmeti = this.state.data.slice(this.indeksPrvogPredmeta, this.indeksPoslednjegPredmeta);
     
  return (
    <div className='MojiPredmeti'>
        <h1>Moji predmeti</h1>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Predmet</th>
                    <th scope="col">ESPB</th>
                </tr>
            </thead>
            <tbody>
                {this.trenutniPredmeti.map((el, index) => <tr><td>{index+1}</td><td>{el.naziv}</td><td>{el.ESPB}</td><td></td></tr>)}
            </tbody>
        </table>
        <Paginacija ukupniPredmeti = {this.state.data.length} 
        elementiPoStrani = {this.elementiPoStrani}
        setTrenutnaStranica = {this.setTrenutnaStranica}/>

    </div>
  );
}}

export default MojiPredmeti