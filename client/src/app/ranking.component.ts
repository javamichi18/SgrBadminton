/**
 * Created by Max on 29.03.2017.
 */
import { Component, OnInit } from '@angular/core';
import { SpielerService } from './spieler.service';
import { Spieler } from './spieler';
import { isNullOrUndefined} from 'util';
import { Globals } from './globals';
import {Observable} from "rxjs";

@Component({
  selector: 'app-ranking',
  template:`
    <ul>
        <li *ngFor="let spieler of values | async">{{spieler.vorname}}</li>
    </ul>
  `,
    providers: [ SpielerService ]
    //templateUrl: './ranking.component.html'
})

export class RankingComponent implements OnInit {
    private values: Observable<Spieler[]>;

    ngOnInit() {
        this.getSpieler(0);
    }
    public getSpieler(index: number): void {
        this.values =this.spielerservice.obsSpielerholen()
    }

/*  public getSpielerderReihe(reihenNr: number): any {//Spieler[] {
    function Summe(y: number): number {
        if (y > 0) {
            return (y > 1 ? y + Summe(y - 1) : 1);
        } else {
            return 0;
        }
    }
    let m = Summe(reihenNr - 1);
    let o = 0;
    /!*let output: Observable<Spieler[]> = [
        new Spieler(),
    ];
    for (m; m < Summe(reihenNr); m++) {
        if (!isNullOrUndefined(this.getSpieler(m))) {
            output[o] = this.getSpieler(m);
            o++;
        }
    }
    return output;*!/
  }

  public istAngemeldet(spieler: Spieler): boolean {
    if (spieler === this.globals.getLoggedIn()) {
      return true;
    } else {
      return false;
    }
  }

  public getIndex(spieler: Spieler): number {
      let n = 0;
      for (n; n < 20; n++) {
          /!*if (this.getSpieler(n) === spieler) {
              return n;
          }*!/
      }
      return null;
  }

  public getReihe(rang: number): number {
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
  }*/

  constructor (private globals: Globals,
    private spielerservice: SpielerService) {
  }
}
