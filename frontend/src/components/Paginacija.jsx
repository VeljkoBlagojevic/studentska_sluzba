import React from 'react'
import './Paginacija.css'

const Paginacija = ({ukupniPredmeti, elementiPoStrani, setTrenutnaStranica}) => {
  console.log(ukupniPredmeti)
  console.log(elementiPoStrani)
  console.log(Math.ceil(ukupniPredmeti/elementiPoStrani))

    let stranice = [];

  for(let i = 1;i<=Math.ceil(ukupniPredmeti/elementiPoStrani);i++){
    stranice.push(i);
  }  

  return (
    <div>{stranice.map((stranica, index)=>{
      return <button className="paginationBtn" margin = "10px" key = {index}
      onClick = {()=>{
        setTrenutnaStranica(stranica);
      }}>{stranica}</button>
    })
    }</div>
  )
}

export default Paginacija