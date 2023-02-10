import React from "react";
import { PieChart, Pie, Cell, Tooltip, Legend } from "recharts";
import axios from "axios";

class GrafikonOcene extends React.Component {
    state = {
        data: {}
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
            let brojSestica = 0;
            let brojSedmica = 0;
            let brojOsmica = 0;
            let brojDevetki = 0;
            let brojDesetki = 0;
            response.data.forEach(zapis => {
              switch(zapis.ocena){
               case 6: brojSestica++; break;
               case 7: brojSedmica++; break;
               case 8: brojOsmica++; break;
               case 9: brojDevetki++; break;
               case 10: brojDesetki++; break;
                  default: break;
              }
            });
            this.setState({
              data:{
                datasets: [
                  {
                     name: "sestice",
                     value: brojSestica,
                  },
                  {
                     name: "sedmice",
                     value: brojSedmica,
                  },
                  {
                     name: "osmice",
                     value: brojOsmica,
                  },
                  {
                     name: "devetke",
                     value: brojDevetki,
                  },
                  {
                     name: "desetke",
                     value: brojDesetki,
                  }
                ]
              }
            });
          }, (error) => {
            console.log(error);
          });
      }

   COLORS = ["#8884d8", "#82ca9d", "#FFBB28", "#FF8042", "#AF19FF"];
   pieData = [
      {
         name: "Apple",
         value: 54.85
      },
      {
         name: "Samsung",
         value: 47.91
      },
      {
         name: "Redmi",
         value: 16.85
      },
      {
         name: "One Plus",
         value: 16.14
      },
      {
         name: "Others",
         value: 10.25
      }
   ];
   CustomTooltip = ({ active, payload, label }) => {
      if (active) {
         return (
         <div
            className="custom-tooltip"
            style={{
               backgroundColor: "#ffff",
               padding: "5px",
               border: "1px solid #cccc"
            }}
         >
            <label>{`${payload[0].name} : ${payload[0].value}`}</label>
         </div>
      );
   }
   return null;
};
render() {
   return (
      <PieChart width={730} height={300}>
      <Pie
         data={this.state.data.datasets}
         color="#000000"
         dataKey="value"
         nameKey="name"
         cx="50%"
         cy="50%"
         outerRadius={120}
         fill="#8884d8"
      >
         {this.pieData.map((entry, index) => (
            <Cell
               key={`cell-${index}`}
               fill={this.COLORS[index % this.COLORS.length]}
            />
         ))}
      </Pie>
      <Tooltip content={<this.CustomTooltip />} />
      <Legend />
      </PieChart>
      );
   }
}
export default GrafikonOcene;