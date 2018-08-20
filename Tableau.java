/* Card Game Perditio Tempus */

/* A standard deck has 52 cards. Each card has one of four suits, called spade, club, heart, and diamond, but suits 
are not used in Perditio Tempus.Each card also has one of thirteen ranks, called ace, two, three, four, five, six, seven, 
eight, nine, ten, jack, queen, and king.These ranks correspond to the numbers 1 through 13, respectively.

Perditio Tempus is played in the following way. You shuffle the deck, and the deal the cards face-down into a row of thirteen 
piles, with four cards in each pile.The row of piles is called the tableau (pronounced tab-LOW). 
The piles are numbered from 1 to 13, so that each pile corresponds to a card rank.
Play now proceeds according to the following rules.

- Set the variable p to 1.

- If pile number p is empty, then go to step 4.

- Turn over the card from the top of pile number p. Reset p to the number given by the card’s rank. 
Throw the card away. Go to step 2.

- If all piles are empty, then you have won. If some pile is not empty, then you have lost.
*/

import java.util.Random;

final class Card
{
//  RANK NAME. Printable names of card ranks.
  private static final String [] rankName =
   {
     "ace",     //   0
     "two",     //   1
     "three",   //   2
     "four",    //   3
     "five",    //   4
     "six",     //   5
     "seven",   //   6
     "eight",   //   7
     "nine",    //   8
     "ten",     //   9
     "jack",    //  10
     "queen",   //  11
     "king"     //  12
   };

//  SUIT NAME. Printable names of card suits.
  private static final String [] suitName =
   {
     "spade",   //  0
     "heart",   //  1
     "diamond", //  2
     "club"     //  3
   };


  private int rank;  //  Card rank, between 0 and 12 inclusive.
  private int suit;  //  Card suit, between 0 and  3 inclusive.


//  CARD. Constructor. Make a new CARD with the given RANK and SUIT.


  public Card(int rank, int suit)
  {
    if (0 <= suit && suit <= 3 && 0 <= rank && rank <= 12)
    {
      this.rank = rank;
      this.suit = suit;
    }
    else
    {
      throw new IllegalArgumentException("No such card.");
    }
  }

//  GET RANK. Return the RANK of this card.


  public int getRank()
  {
    return rank;
  }

//  GET SUIT. Return the SUIT of this card.

  public int getSuit()
  {
    return suit;
  }

//  TO STRING. Return a string that describes this card, for printing only. For
//  example, we might return "the queen of diamonds" or "the ace of hearts".


  public String toString()
  {
    return "the " + rankName[rank] + " of " + suitName[suit] + "s";
  }
}


class Deck
{
  private Card cards[];
  private int count;// number of cards that have been dealt 
  public Deck()
  {
    count=0;
    cards=new Card[52];
    int t=0;
    for(int i=0;i<4;i++)
    {
      for(int j=0;j<13;j++)
      {
        cards[t++]=new Card(j,i);
      }
    }
  }

  public void shuffle()
  {
    if(count!=0)
    throw new IllegalArgumentException("Can not shuffle when game has begun");
    else
    {
      Random r=new Random();
      for(int i=51;i>=1;i=i-1)
      {
        int j=Math.abs(r.nextInt()) % i;
        Card temp=cards[i];
        cards[i]=cards[j];
        cards[j]=temp;
      }
    }
  }


  public boolean canDeal()
  {
    return count!=52;
  }

  public Card deal()
  {
    if(canDeal())
    {
      count=count+1;
      return cards[count-1];
    }
    else
    {
      throw new IllegalStateException("No card to be dealt");
    }
  }
}


public class Tableau
{
  private class Pile
  {
    private Pile next;
    private Card card;
    private Pile(Card card, Pile next)
    {
      this.card=card;
      this.next=next;
    }
  }
   private Pile top;
   public Tableau()
   {
     top=null;
   }

  private void addPile(Card card)
  {
    top=new Pile(card,top);
    System.out.println("Added "+card.toString());
  }

  private boolean canMerge()
  {
  if(hasManyPiles())
  {
    return canPutOn(top.card,top.next.card);
  }
  else 
    return false;
  }

  private boolean canPutOn(Card left, Card right)
  {
    if(left.getRank()>right.getRank())
      return true;
    else if(left.getSuit()==right.getSuit())
      return true;
    else return false;
  }


  private boolean hasManyPiles()
  {
    if(top==null || top.next==null)
      return false;
    else return true;
  }

  private void mergeTwoPiles()
  {
    if(canMerge())
    {
      System.out.println("Merged "+ top.card.toString()+ " and " + top.next.card.toString());
      top.next=top.next.next;
    }
 }

  private void results()
  {
    if(top.next==null)
      System.out.println("Won the game");
      else System.out.println("Lost the game");
  }


  public void play()
  {
    Deck d=new Deck();
    d.shuffle();
    while(d.canDeal())
    {
      mergeTwoPiles();
      addPile(d.deal());
      mergeTwoPiles();
    }
    results();
    System.out.println();
  }
}

    
class Driver
{
  public static void main(String args[])
  {
    Tableau t=new Tableau();
    t.play();
  }
}
