import urlBase from '../App';
import axios from 'axios';



export default function prijava(){
    alert();
    axios({
      method: 'post',
      url: urlBase + '/api/v1/auth/authenticate',
      data: {
        username: 'nv20190062',
        password: 'nikola123'
      }
    });
  }