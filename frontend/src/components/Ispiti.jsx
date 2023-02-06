import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

class Ispiti extends React.Component {
    state = {
      data: [],
      dataNeuspesno: [],
      dataUspesno: [],
      tip: false
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
          this.setState({dataUspesno: response.data})
          console.log(this.state.dataUspesno);
        }, (error) => {
          console.log(error);
        });
        axios({
            method: 'get',
            url: '/api/v1/polaganja/neuspesna',
            baseURL: 'http://localhost:8080',
            data: {},
            headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
          }).then((response) => {
            console.log(response);
            this.setState({dataNeuspesno: response.data})
            console.log(this.state.dataNeuspesno);
          }, (error) => {
            console.log(error);
          });
    }

    plsRadi() {
        this.setState({tip: !this.state.tip});
    }

    /*updateUspesna(){
        axios({
            method: 'get',
            url: '/api/v1/polaganja/uspesna',
            baseURL: 'http://localhost:8080',
            data: {},
            headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
          }).then((response) => {
            console.log(response);
            this.setState({data: response.data})
            console.log(this.state.dataUspesno);
          }, (error) => {
            console.log(error);
          });
    }
    updateNeuspesna(){
        axios({
            method: 'get',
            url: '/api/v1/polaganja/neuspesna',
            baseURL: 'http://localhost:8080',
            data: {},
            headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
          }).then((response) => {
            console.log(response);
            this.setState({data: response.data})
            console.log(this.state.dataNeuspesno);
          }, (error) => {
            console.log(error);
          });
    }*/
      
    render(){
  return (
    <div className='Ispiti'>
        <h1>Ispiti</h1>
        <div class="status">
            <form>
            <button class="statusMolbe" onClick={this.plsRadi}>Uspesna polaganja</button>
            <button class="statusMolbe" onClick={this.plsRadi}>Neuspesna polaganja</button>
        </form>
        </div>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Predmet</th>
                    <th scope="col">ESPB</th>
                    <th scope="col">Ocena</th>
                </tr>
            </thead>
            <tbody>
                    {(this.state.tip ? this.state.dataUspesno : this.state.dataNeuspesno).map(el => <tr><td>x</td><td>{el.predmet?.naziv}</td><td>{el.predmet?.espb}</td><td>{el.ocena}</td><td></td></tr>)}
                <tr>
                    <h5>Prosecna ocena:</h5> 
                </tr>
            </tbody>
        </table>
    </div>
  );
}}

export default Ispiti