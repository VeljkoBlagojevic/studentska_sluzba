import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
 
 
class Student extends React.Component {
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
        <div className='Student'>
            <table class="table">
                <tbody>
                    <tr>
                        <th><h1>Podaci o studentu</h1></th>
                    </tr>
                    <tr>
                        <th>Broj indeksa</th>
                        <td>{this.state.data?.indeks}</td>
                    </tr>
                    <tr>
                        <th>Ime i prezime</th>
                        <td>{this.state.data?.ime + " " + this.state.data?.prezime}</td>
                    </tr>
                    <tr>
                        <th>Username</th>
                        <td>{this.state.data?.username}</td>
                    </tr>
                    <tr>
                        <th>Ime roditelja</th>
                        <td>{this.state.data?.imeRoditelja}</td>
                    </tr>
                    <tr>
                        <th>JMBG</th>
                        <td>{this.state.data?.jmbg}</td>
                    </tr>
                    <tr>
                        <th>Mesto rodjenja</th>
                        <td>{this.state.data?.mestoRodjenja}</td>
                    </tr>
                    <tr>
                        <th>Licni email</th>
                        <td>{this.state.data?.licniEmail}</td>
                    </tr>
                    <tr>
                        <th>Studentski email</th>
                        <td>{this.state.data?.studentskiEmail}</td>
                    </tr>
                    <tr>
                        <th>Broj telefona</th>
                        <td>{this.state.data?.brojTelefona}</td>
                    </tr>
                    
                </tbody>
            </table>
            <div class="student2">
                <img style={{width: "300px", padding: "5px"}} src={this.state.data.slika} alt="" />
            </div>
        </div>
      );
  }
}
 
export default Student;