import React, { useState } from 'react'
import axios from 'axios'

const Admin = () => {
  const [IPAdresa, setIPAdresa] = useState("");
  const [dugme1Text, setDugme1Text] = useState("Prikazi trenutnu javnu IP adresu");
  const [dugme2Text, setDugme2Text] = useState("Prikaži dodatne informacije o adresi");

  const [IPInfo, setIPInfo] = useState({
    asn: '',
    city: '',
    continent_code: '',
    country: '',
    coutry_area: '',
    country_calling_code: '',
    country_capital: '',
    country_code: '',
    country_code_iso3: '',
    country_name: '',
    currency: '',
    region: '',
    timezone: '',
  })

  const dugme1Action = () => {
    if(dugme1Text === "Prikazi trenutnu javnu IP adresu"){
    axios.get("https://api.ipify.org/?format=json").then((response) =>{
      setIPAdresa(response.data.ip)
      setDugme1Text("Sakrij trenutnu javnu IP adresu")
    })
    } else {
    setIPAdresa("")
    console.log("Sakrivena ip adresa.")
    setDugme1Text("Prikazi trenutnu javnu IP adresu")
    }
  }  
  
  const getIPDATA = async () => {
   const odgovor =  await axios.get("https://ipapi.co/json/");
   console.log(odgovor.data)
   setIPInfo(odgovor.data)
   setDugme2Text("Sakrij podatke")
  }
  const dugme2Action = () => {
    if(IPAdresa === "" && dugme2Text === "Prikaži dodatne informacije o adresi"){
      alert("Nije poznata IP adresa. Da biste dobili ove podatke, pritisnite prvo dugme")
    } else if(dugme2Text === "Prikaži dodatne informacije o adresi"){
      getIPDATA();
    } else {
      setIPInfo(
        {
          asn: '',
          city: '',
          continent_code: '',
          country: '',
          coutry_area: '',
          country_calling_code: '',
          country_capital: '',
          country_code: '',
          country_code_iso3: '',
          country_name: '',
          currency: '',
          region: '',
          timezone: '',
        }
      );
      setDugme2Text("Prikaži dodatne informacije o adresi")
    }
    }
  
    return (
    <>
    <h1>Podaci za administratora</h1>
    <p>
      U ovoj sekciji možete dobiti dodatne informacije o javnoj IP adresi servisa i druge servisne informacije.
    </p>
    <button className='btn btn-danger' onClick={dugme1Action}>{dugme1Text}</button>
    <br />
    <h2 >{IPAdresa}</h2>
    <button className='btn btn-danger' onClick={dugme2Action}>{dugme2Text}</button>
    <br />
    <br />
      {IPInfo.asn === '' ? <></> : (
    <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Ključ</th>
                    <th scope="col">Vrednost</th>
                </tr>
            </thead>
            <tbody>
                    <tr>
                      <td>1</td>
                      <td>asn</td>
                      <td>{IPInfo.asn}</td>
                    </tr>
                    <tr>
                      <td>2</td>
                      <td>grad</td>
                      <td>{IPInfo.city}</td>
                    </tr>
                    <tr>
                      <td>3</td>
                      <td>kontinent</td>
                      <td>{IPInfo.continent_code}</td>
                    </tr>
                    <tr>
                      <td>4</td>
                      <td>država</td>
                      <td>{IPInfo.country}</td>
                    </tr>
                    <tr>
                      <td>5</td>
                      <td>region ID</td>
                      <td>{IPInfo.country_area}</td>
                    </tr>
                    <tr>
                      <td>6</td>
                      <td>pozivni kod</td>
                      <td>{IPInfo.country_calling_code}</td>
                    </tr>
                    <tr>
                      <td>7</td>
                      <td>prestonica</td>
                      <td>{IPInfo.country_capital}</td>
                    </tr>
                    <tr>
                      <td>8</td>
                      <td>državni kod</td>
                      <td>{IPInfo.country_code}</td>
                    </tr>
                    <tr>
                      <td>9</td>
                      <td>državni kod iso3</td>
                      <td>{IPInfo.country_code_iso3}</td>
                    </tr>
                    <tr>
                      <td>10</td>
                      <td>naziv države</td>
                      <td>{IPInfo.country_name}</td>
                    </tr>
                    <tr>
                      <td>11</td>
                      <td>valuta</td>
                      <td>{IPInfo.currency}</td>
                    </tr>
                    <tr>
                      <td>12</td>
                      <td>region</td>
                      <td>{IPInfo.region}</td>
                    </tr>
                    <tr>
                      <td>13</td>
                      <td>vremenska zona</td>
                      <td>{IPInfo.timezone}</td>
                    </tr>
            </tbody>
        </table>
    )}
        
    </>
  )
}

export default Admin