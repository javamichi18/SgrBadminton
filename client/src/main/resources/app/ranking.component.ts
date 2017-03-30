/**
 * Created by Max on 29.03.2017.
 */
import {Component} from '@angular/core';
import { SPIELER } from './mock-spieler';
import { Spieler } from './spieler';
import {isNullOrUndefined} from 'util';
import { Globals } from './globals';


let spielerzaehler = -1;
let zeiger = spielerzaehler;
@Component({
  selector: 'app-ranking',
  templateUrl: './ranking.component.html'
})
export class RankingComponent {
  globals: Globals;
  protected getSpieler(): Spieler[] {
    return SPIELER;
  }
  getSpielerproReihe(ReihenNr: number): number {
    let SpielerAnzahl = 0;
    do {
      SpielerAnzahl++;
    }
    while (isNullOrUndefined(this.getSpieler()[SpielerAnzahl]));
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
  getNeuenSpieler(): number {
    spielerzaehler = spielerzaehler + 1;
    zeiger = spielerzaehler;
    return spielerzaehler;
  }
  getAktuellenSpieler(): number {
    return zeiger;
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
  }
}
