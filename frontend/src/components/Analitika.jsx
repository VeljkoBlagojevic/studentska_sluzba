import React from 'react';
import axios from 'axios';
import Grafikon from './Grafikon';
import GrafikonOcene from './GrafikonOcene';
import './Analitika.css';

class Analitika extends React.Component {
    state = {
        data: []
    }

    componentDidMount() {
        axios({
            method: 'get',
            url: '/api/v1/studenti/trenutni',
            baseURL: 'http://localhost:8080',
            data: {},
            headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
          }).then((response) => {
            console.log(response);
            this.setState({data: response.data})
            //alert(JSON.stringify(response.data.mestoRodjenja.naziv));
            
        }, (error) => {
            console.log(error);
        });
    }

    render() {
    return (
        <>
        <h3>Podaci o polaganju ispita studenta: </h3>    
        <h4>{this.state.data?.ime} {this.state.data?.prezime}</h4>
        <br></br>
        <h3>Sa brojem indeksa: </h3>
        <h4>{this.state.data?.indeks}</h4>
        <div className='container'>
            <div className = "child">
        <Grafikon ></Grafikon>
            </div>
            <div className = "child">
        <GrafikonOcene></GrafikonOcene>
        </div>
        </div>
        </>
        
      );
  }
}

export default Analitika;