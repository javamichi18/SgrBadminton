/**
 * Created by Max on 29.03.2017.
 */
import {Component} from '@angular/core';
import { SPIELER } from './mock-spieler';
import { Spieler } from './spieler';
import { isNullOrUndefined} from 'util';
import { Globals } from './globals';

@Component({
  selector: 'app-ranking',
  templateUrl: './ranking.component.html'
})

export class RankingComponent {
  globals: Globals;

  getSpielerArray(): Spieler[] {
    return SPIELER;
  }

  public getSpieler(index: number): Spieler {
    return this.getSpielerArray()[index];
  }

  public getSpielerderReihe(reihenNr: number): Spieler[] {
    let m = 0;
    function Summe(y: number): number {
        if (y>0) {
            return (y > 1 ? y + Summe(y - 1) : 1);
        } else {
            return 0;
        }
    }
    m = Summe(reihenNr - 1);
    let o = 0;
    let output: Spieler [] = [
        new Spieler(),
    ];
    for (m; m < Summe(reihenNr); m++) {
        if (!isNullOrUndefined(this.getSpieler(m))) {
            output[o] = this.getSpieler(m);
            o++;
        }
    }
      self.console.log(reihenNr);
    for (let b=0; b < output.length; b++) {
        self.console.log(output[b].vorname);
    }
    return output;
  }

  public istAngemeldet(spielerNr: number): boolean {
    if (this.getSpieler(spielerNr) === this.globals.getLoggedIn()) {
      return true;
    } else {
      return false;
    }
  }

  public getIndex(spieler: Spieler): number {
      let n = 0;
      for (n; n < 20; n++) {
          if (this.getSpieler(n) === spieler) {
              return n;
          }
      }
      return null;
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
      if (this.globals.getLoggedIn().rang > rang && (this.globals.getLoggedIn().rang - rang <= this.getReihe(this.globals.getLoggedIn().rang) - 1)  ) {
          return true;
      } else {
          if (this.globals.getLoggedIn().rang <= 5 && (rang < this.globals.getLoggedIn().rang) ) {
              return true;
          } else {
              return false;
          }
      }
  }

  constructor (global: Globals) {
  this.globals = global;
  this.globals.setLoggedIn(this.getSpieler(15));
  }
}
