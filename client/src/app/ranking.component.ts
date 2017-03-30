/**
 * Created by Max on 29.03.2017.
 */
import {Component} from '@angular/core';
import { SPIELER } from './mock-spieler';
import { Spieler } from './spieler';
import {isNullOrUndefined} from 'util';
import { Globals } from './globals';


let spielerzaehler = -1;

@Component({
  selector: 'app-ranking',
  templateUrl: './ranking.component.html'
})

export class RankingComponent {
  i: number;
  globals: Globals;
  spielerInDerReihe: number;
  reihe: number;
  getSpielerArray(): Spieler[] {
    return SPIELER;
  }
  getSpieler(index: number): Spieler {
    return this.getSpielerArray()[index];
  }

  getSpielerproReihe(ReihenNr: number): number {
    let SpielerAnzahl = 0;
    do {
      SpielerAnzahl++;
    }
    while (isNullOrUndefined(this.getSpielerArray()[SpielerAnzahl]));
    let i = 1;
    do {
      if (SpielerAnzahl >= i) {
        SpielerAnzahl = SpielerAnzahl - i;
      } else {
        SpielerAnzahl = 0;
      }
      i++;
    } while (i <= ReihenNr);
    return SpielerAnzahl;
  }


  getReihe(rang: number): number {
    let row = 1;
    let countGesamt = row;
    if (rang > 1) {
      do {
        row++;
        countGesamt += row;
      } while (countGesamt < rang);
    }
    return row;
  }

  kannFordern(rang: number): boolean {
    if (this.globals.getLoggedIn().rang < rang && this.globals.getLoggedIn().rang <= 5 ) {
      return true;
    } else {
      if (this.globals.getLoggedIn().rang > rang) {
        return false;
      }
      if (rang > this.globals.getLoggedIn().rang - (this.getReihe(this.globals.getLoggedIn().rang) - 1)) {
        return true;
      } else {
        return false;
      }
    }
  }

  constructor (global: Globals) {
  this.globals = global;
  this.globals.setLoggedIn(this.getSpieler(5));
  this.reihe = 1;
  this.spielerInDerReihe = 0;
  this.i = 1;
  }
}
