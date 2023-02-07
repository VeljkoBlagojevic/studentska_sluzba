import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

class IspitiUspesno extends React.Component {
    state = {
      data: [],
      prosek: 0,
      ukupnoESPB: 0
    }
    
    componentDidMount() {
      axios({
          method: 'get',
          url: '/api/v1/polaganja/uspesna',
          baseURL: 'http://localhost:8080',
          data: {},
          headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
        }).then((response) => {
          console.log(response);
          this.setState({data: response.data});
          console.log(this.state.data);
          this.setState({prosek: (this.state.data.map(el => el.ocena).reduce((partialSum, a) => partialSum + a, 0))/this.state.data.length });
          this.setState({ukupnoESPB: (this.state.data.map(el => el.predmet.ESPB).reduce((partialSum, a) => partialSum + a, 0)) });
        }, (error) => {
          console.log(error);
        });
    }
      
    render(){
  return (
    <div className='Ispiti'>
        <h1>Ispiti</h1>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Predmet</th>
                    <th scope="col">ESPB</th>
                    <th scope="col">Ocena</th>
                    <th scope="col">Datum</th>
                </tr>
            </thead>
            <tbody>
                    {this.state.data.map((el, index) => <tr><td>{index+1}</td><td>{el.predmet?.naziv}</td><td>{el.predmet?.ESPB}</td><td>{el.ocena}</td><td>{el.datum}</td><td></td></tr>)}
                <tr>
                    <h5>Prosecna ocena: {this.state.prosek}</h5> 
                    <h5>Ukupno osvojeno ESPB: {this.state.ukupnoESPB}</h5> 
                </tr>
            </tbody>
        </table>
    </div>
  );
}}

export default IspitiUspesno