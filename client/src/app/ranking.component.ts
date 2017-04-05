/**
 * Created by Max on 29.03.2017.
 */
import { Component, OnInit } from '@angular/core';
import { SpielerService } from './spieler.service';
import { Spieler } from './spieler';
import { Globals } from './globals';
import {Observable} from "rxjs";

@Component({
    selector: 'app-ranking',
    templateUrl: './ranking.component.html',
    providers: [ SpielerService ]
})

export class RankingComponent implements OnInit {
    private values: Observable<Spieler[]>;
    private rang: Observable<number>;
    private fordern: Observable<boolean[]>;
    private spielerArray: Spieler[] = [];
    private KannFordern: boolean[];
    private meinRang: number;

    ngOnInit() {
        this.values = this.spielerservice.obsSpielerholen();
        this.rang = this.spielerservice.obsRangholen();
        this.fordern = this.spielerservice.obskannFordernholen();
    }

    istRangAktuellerSpieler(rang: number, spieler: Spieler): boolean {
        /*if (this.spielerArray[this.spielerArray.length - 1] != spieler) {
            this.spielerArray[this.spielerArray.length] = spieler;
            console.log(this.spielerArray[this.spielerArray.length - 1])
        }*/

        if(this.meinRang === rang)
            return true;
        else
            return false;
    }

    kannFordern(rang: number): boolean {
        return this.KannFordern[rang];
    }

    RangRausschreiben(erg: number) {
        this.meinRang = erg;
    }

    FordernRausschreiben(erg: boolean) {
        this.KannFordern[this.KannFordern.length] = erg;
    }

    constructor (private globals: Globals,
        private spielerservice: SpielerService) {
    }
}
