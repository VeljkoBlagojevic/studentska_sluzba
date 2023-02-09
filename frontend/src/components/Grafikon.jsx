import React, { PureComponent } from 'react';
import { PieChart, Pie, Tooltip } from 'recharts';
import axios from 'axios';

export default class Grafikon extends PureComponent {
  static demoUrl = 'https://codesandbox.io/s/two-simple-pie-chart-otx9h';

  state = {
    data: {}
  }
  
  componentDidMount() {
    axios({
        method: 'get',
        url: '/api/v1/polaganja',
        baseURL: 'http://localhost:8080',
        data: {},
        headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
      }).then((response) => {
        console.log(response);
        let brojPolozenih = 0;
        let brojPalih = 0;
        response.data.forEach(zapis=>{
          if(zapis.polozio === true){
            brojPolozenih++;
          }else brojPalih++;
        });
        this.setState({
          data:{
            datasets: [
              {
                name: "polozeni",
                value: brojPolozenih,
              },
              {
                name: "nepolozeni",
                value: brojPalih,
              }
            ]
          }
        });
      }, (error) => {
        console.log(error);
      });
  }
  
  render() {
    console.log(this.state.data.datasets)
    return (
      <div>
        <PieChart width={250} height={250}>
          <Pie
            dataKey="value"
            isAnimationActive={false}
            data={this.state.data.datasets}
            cx="50%"
            cy="50%"
            outerRadius={80}
            fill="#AA4A44"
            label
          />
          <Tooltip />
        </PieChart>
        </div>
    );
  }
}