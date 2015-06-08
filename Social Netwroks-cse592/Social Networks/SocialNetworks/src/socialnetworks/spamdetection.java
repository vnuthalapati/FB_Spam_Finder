package socialnetworks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class spamdetection {
	public static int num;
	int index;
	int friends_count;
	int friend_request_send;
	int firend_request_rxd;
	int messages_Send;
	int messages_rxd;
	int tags;
	int urls;
	int posts;
	float legitfactor1;
	float legitfactor2;
	public float getLegitfactor3() {
		return legitfactor3;
	}

	public void setLegitfactor3(float legitfactor3) {
		this.legitfactor3 = legitfactor3;
	}

	float legitfactor3;
	int spamcount;
	public int getSpamcount() {
		return spamcount;
	}

	public void setSpamcount(int spamcount) {
		spamcount++;
		this.spamcount = spamcount;
	}

	public int getHamcount() {
		return hamcount;
	}

	public void setHamcount(int hamcount) {
		hamcount++;
		this.hamcount = hamcount;
	}

	int hamcount;
	String spam;
	
	

	public String getSpam() {
		return spam;
	}

	public void setSpam(String spam) {
		this.spam = spam;
	}

	public float getLegitfactor1() {
		return legitfactor1;
	}

	public void setLegitfactor1(float legitfactor1) {
		this.legitfactor1 = legitfactor1;
	}

	public float getLegitfactor2() {
		return legitfactor2;
	}

	public void setLegitfactor2(float legitfactor2) {
		this.legitfactor2 = legitfactor2;
	}
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getFriends_count() {
		return friends_count;
	}

	public void setFriends_count(int friends_count) {
		this.friends_count = friends_count;
	}

	public int getFriend_request_send() {
		return friend_request_send;
	}

	public void setFriend_request_send(int friend_request_send) {
		this.friend_request_send = friend_request_send;
	}

	public int getFirend_request_rxd() {
		return firend_request_rxd;
	}

	public void setFirend_request_rxd(int firend_request_rxd) {
		this.firend_request_rxd = firend_request_rxd;
	}

	public int getMessages_Send() {
		return messages_Send;
	}

	public void setMessages_Send(int messages_Send) {
		this.messages_Send = messages_Send;
	}

	public int getMessages_rxd() {
		return messages_rxd;
	}

	public void setMessages_rxd(int messages_rxd) {
		this.messages_rxd = messages_rxd;
	}

	public int getTags() {
		return tags;
	}

	public void setTags(int tags) {
		this.tags = tags;
	}

	public int getUrls() {
		return urls;
	}

	public void setUrls(int urls){ 
		this.urls = urls;
	}

	public int getPosts() {
		return posts;
	}

	public void setPosts(int posts) {
		this.posts = posts;
	}

	private static final String delimiter = ",";
	//function to test spam or ham
	public int spamtest(String test_string[]) throws FileNotFoundException{
		
		File f = new File("src/socialnetworks/train");
		double spam=0,ham=0;
		HashMap <String,Double> hamhm = new HashMap <String,Double>();
		HashMap <String,Double> spamhm = new HashMap <String,Double>();
		HashMap <String,Double> totalhm = new HashMap <String,Double>();
		HashMap <String,Double> pxspam = new HashMap <String,Double>();
		HashMap <String,Double> pxham = new HashMap <String,Double>();
		FileReader fr = new FileReader(f);
		Scanner sc = new Scanner(fr);
		String s;
		while(sc.hasNextLine())
		{
			s = sc.nextLine();
			String [] splited = s.split(" ");
			if(splited[1].equals("ham"))
			{
				for(int i=2 ; i<splited.length ; i=i+2)
				{
					if(hamhm.get(splited[i])!=null)
						hamhm.put(splited[i],hamhm.get(splited[i])+Double.parseDouble(splited[i+1]));
					else
						hamhm.put(splited[i],1.0);
					if(totalhm.get(splited[i])!=null)
						totalhm.put(splited[i],totalhm.get(splited[i])+Double.parseDouble(splited[i+1]));
					else
						totalhm.put(splited[i],1.0);
				}
				ham++;
			}
			else
			{
				for(int i=2 ; i<splited.length ; i=i+2)
				{
					if(spamhm.get(splited[i])!=null)
						spamhm.put(splited[i],spamhm.get(splited[i])+Double.parseDouble(splited[i+1]));
					else
						spamhm.put(splited[i],1.0);
					if(totalhm.get(splited[i])!=null)
						totalhm.put(splited[i],totalhm.get(splited[i])+Double.parseDouble(splited[i+1]));
					else
						totalhm.put(splited[i],1.0);
				}
				spam++;
			}
			//System.out.println(s);
			
		}
		
		double pspam = spam/(spam+ham);
		double pham = ham/(spam+ham);
		double total_spam_words=0;
		double total_ham_words=0;
		double total_unique_words = totalhm.size();
		for(Double x:spamhm.values())
			total_spam_words += x;
		for(Double x:hamhm.values())
			total_ham_words += x;
		for(Map.Entry<String,Double> e:spamhm.entrySet())
			pxspam.put((String)e.getKey(), (double)e.getValue()/(total_spam_words+total_unique_words));
		for(Map.Entry<String,Double> e:hamhm.entrySet())
			pxham.put((String)e.getKey(), (double)e.getValue()/(total_ham_words+total_unique_words));
	//	String test_string[] = "If you don't have any referrals in Microsoft then apply for it during career fair in September".split(" ");
		double test_spam=1.0,test_ham=1.0;
		for(String word:test_string)
		{
			if(pxspam.get(word)!=null)
			{
				test_spam *= (double)pxspam.get(word);
				
			}
		}
		test_spam *= pspam;
	//	System.out.println(pspam);
		for(String word:test_string)
		{
			if(pxham.get(word)!=null)
			{
				test_ham *= (double)pxham.get(word);
			}
		}
		test_ham *= pham;
		//System.out.println(test_spam+"\t"+test_ham);
		if(test_spam>test_ham){
			//System.out.println("Spam!!!");
			sc.close();
			return 1;
		}else{
			//System.out.println("Ham!!!");
			sc.close();
			return 0;
		}
	}
		
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		int counter=0;
		int a=0,b=0,c=0,d=0,ei=0,f=0,g=0,h=0;
		Double avg_friends=0.0,avg_tags=0.0,avg_posts=0.0,avg_urls=0.0,avg_ratio1=0.0,avg_ratio2=0.0;
		BufferedReader fileReader = null;
		List<spamdetection> users = new ArrayList();
		try{
		String line = "";
		//System.out.println(S)
		fileReader = new BufferedReader(new FileReader("src/socialnetworks/data8.csv"));
		fileReader.readLine();
			while((line=fileReader.readLine())!=null){
				String fields[] = line.split(delimiter);
					if(fields.length>0){
						spamdetection sp = new spamdetection();
						sp.setIndex(++num);
						//System.out.println(sp.getIndex());
						sp.setFriends_count(Integer.parseInt(fields[1]));
						//System.out.println(sp.getFriends_count());zbznn
						sp.setFriend_request_send(Integer.parseInt(fields[3]));
						//System.out.println(sp.getFriend_request_send());
						sp.setFirend_request_rxd(Integer.parseInt(fields[2]));
						//System.out.println(sp.getFirend_request_rxd());
						sp.setMessages_Send(Integer.parseInt(fields[4]));
						sp.setMessages_rxd(Integer.parseInt(fields[5]));
						sp.setTags(Integer.parseInt(fields[6]));
						//System.out.println(sp.getTags());
						sp.setUrls(Integer.parseInt(fields[7]));
						sp.setPosts(Integer.parseInt(fields[8]));
						sp.setSpam(fields[9]);
						sp.setLegitfactor1(1000);
						sp.setLegitfactor2(1000);
						sp.setLegitfactor3(0);
						sp.setSpamcount(0);
						sp.setHamcount(0);
						users.add(sp);
					}
			
			}
			//average no of friends in ego network
			for(spamdetection sp : users){
				avg_friends = avg_friends + sp.getFriends_count();				
			}
			avg_friends = Math.floor(avg_friends/num);
			System.out.println("average no of friends are "+avg_friends);
			//average no of tags in ego network
			for(spamdetection sp : users){
				avg_tags = avg_tags + sp.getTags();				
			}
			avg_tags = Math.floor(avg_tags/num);
			System.out.println("average no of tags are "+avg_tags);
			//average no of posts in ego network
			for(spamdetection sp : users){
				avg_posts = avg_posts + sp.getPosts();				
			}
			avg_posts = Math.floor(avg_posts/num);
			System.out.println("average no of posts are "+avg_posts);
			//average no of urls in ego network
			for(spamdetection sp : users){
				avg_urls = avg_urls + sp.getUrls();				
			}
			avg_urls = Math.floor(avg_urls/num);
			System.out.println("average no of urls are "+avg_urls);
			//average ratio of friend rqsts rxd/send
			for(spamdetection sp : users){
				avg_ratio1 = avg_ratio1 + (sp.getFirend_request_rxd()/sp.getFriend_request_send());				
			}
			avg_ratio1 = (avg_ratio1/num);
			System.out.println("average ratio1 is "+avg_ratio1);
			//average ratio of msgs rxd/send
			for(spamdetection sp : users){
				avg_ratio2 = avg_ratio2 + (sp.getMessages_rxd()/sp.getMessages_Send());				
			}
			avg_ratio2 = (avg_ratio2/num);
			System.out.println("average ratio2 is "+avg_ratio2);
			
			//Category 1:consider ratio of no of friend requests rxd/send, no of msgs send/rxd
			//Criteria:spammers will have this ratios less in number compared to legitimate users
			for(spamdetection sp : users){
				float ratio1 = (float)sp.getFirend_request_rxd()/sp.getFriend_request_send();
					if(((avg_ratio1 - ratio1) < 1 && (avg_ratio1 - ratio1) > 0) ||((ratio1 - avg_ratio1) < 1 && (ratio1 - avg_ratio1) > 0)){
					//	System.out.println("val is "+(ratio1)* 100);
						if(Math.abs(avg_ratio1 - ratio1)<0.5){
						sp.setLegitfactor1(1000 - (float)(500-(Math.abs(avg_ratio1 - ratio1)* 1000)));
						}else{
						sp.setLegitfactor1(1000 - (float)(Math.abs(avg_ratio1 - ratio1)* 1000));	
						}
					}
					if(sp.getFriends_count()<avg_friends){
						float value = sp.getLegitfactor1();
						value = value - 200;
						sp.setLegitfactor1(value);
					}
			}
			
			//Category 2:consider tags,no of messages,posts
			//Criteria:spammers have less tags ,less number of friends and more posts
			for(spamdetection sp : users){
				float ratio2 = (float)sp.getMessages_rxd()/sp.getMessages_Send();
				//System.out.println("rati02 is"+ratio2);
				if(((avg_ratio2 - ratio2) < 1 && (avg_ratio2 - ratio2) > 0) || ((ratio2-avg_ratio2) < 1 && (ratio2 - avg_ratio2) > 0)){
					if(Math.abs(avg_ratio2 - ratio2)<0.5){
							sp.setLegitfactor2(1000 - (float)(500-(Math.abs(avg_ratio2 - ratio2)* 1000)));
					}else{
							sp.setLegitfactor2(500);	
					}
				}
				if(sp.getPosts()<avg_posts){
					float value = sp.getLegitfactor2();
					value = value - 100;
					sp.setLegitfactor2(value);			
				}	
				if(sp.getTags()< avg_tags){
					float value = sp.getLegitfactor2();
					value = value - 100;
					sp.setLegitfactor2(value);
				}
			}
			
			//category 3:bayes filter
			//criteria:spam content in the message
			for(spamdetection sp:users){
				String si[] = sp.getSpam().split(";");
				int i=0;
				for(i=0;i<si.length;i++){
					//System.out.println(si[i]);
				String str[] = si[i].split(" ");
				int j = sp.spamtest(str);
					if(j==1){
						sp.setSpamcount(sp.getSpamcount());	
					}else{
						sp.setHamcount(sp.getHamcount());
					//System.out.println(sp.getHamcount());
					}
				}
				
				float ra = 0;
				if(sp.getHamcount()!=0){
					ra = (float)(sp.getSpamcount()/sp.getHamcount());
						//System.out.println("ratio is "+ra);
				}else{
					ra = 1;
				}
				if(ra >= 1){
					sp.setLegitfactor3(1);
				}else{
					sp.setLegitfactor3(ra);
				}
			}
			
			
			//printing spammers id's
			for(spamdetection sp :users){
				//System.out.print("lgt 1 is "+sp.getLegitfactor1() +"");
				//System.out.println(" lgt 2 is "+sp.getLegitfactor2());
				//sp.getLegitfactor1()<500 && sp.getLegitfactor2()<500 && 
				if(sp.getLegitfactor1()<400 && sp.getLegitfactor2() <500 && sp.getLegitfactor3()==1){
					a++;
					System.out.println("id is "+sp.getIndex());
					//System.out.println("Totally Spam account-Created with fake details and sends messages that contain spam "+ sp.getIndex());
					//System.out.println(sp.getSpam());
				}else if(sp.getLegitfactor1()>400 && sp.getLegitfactor2() <500 && sp.getLegitfactor3()==1){
					b++;
					System.out.println("id is "+sp.getIndex());
					//System.out.println("Have a good network,but they are not active and sometimes sends messages which contains spam "+ sp.getIndex());
					//further network analysis should be done				
				}else if(sp.getLegitfactor1()<400 && sp.getLegitfactor2() >500 && sp.getLegitfactor3()==1){
					c++;
					System.out.println("id is "+sp.getIndex());
					//System.out.println("Poor Network,but they are active interms of posts and tags and messages contain spam"+sp.getIndex());
				}else if(sp.getLegitfactor1()>400 && sp.getLegitfactor2() >500 && sp.getLegitfactor3()==1){
					d++;
					System.out.println("id is "+sp.getIndex());
					//System.out.println("active spammers with good network and more no of average posts"+sp.getIndex());
				}else if (sp.getLegitfactor1()<400 && sp.getLegitfactor2() <500 && sp.getLegitfactor3()==0){
		//			System.out.println("users who are not at all active in fb"+sp.getIndex());
					if(sp.getLegitfactor2()<300 && sp.getLegitfactor1()<300 && sp.getSpamcount()==0){
						//System.out.println(sp.getLegitfactor2());
						//System.out.println("Real account in intial stages "+sp.getIndex());
					}else if(sp.getLegitfactor2()<300 && sp.getLegitfactor1()<300 && sp.getSpamcount()>3){
						//System.out.println(sp.getLegitfactor2());
						ei++;
						//System.out.println("Might be a spam account in future "+sp.getIndex());
					}
				}else if (sp.getLegitfactor1()>400 && sp.getLegitfactor2() <500 && sp.getLegitfactor3()==0){
				//	System.out.println("users with more network but with less no of posts-ex:Celebrities"+sp.getIndex());
					if(sp.getLegitfactor2()<300){
						//System.out.println(sp.getLegitfactor2());
						a++;
					//	System.out.println("spam account just with network"+sp.getIndex());
					}
				}else if (sp.getLegitfactor1()<400 && sp.getLegitfactor2() >500 && sp.getLegitfactor3()==0){
					//System.out.println("a general user profile"+sp.getIndex());
					g++;
				}else if(sp.getLegitfactor1()>400 && sp.getLegitfactor2()>500 && sp.getLegitfactor3()==0){
					//System.out.println("a more active user profile"+sp.getIndex());
					h++;
				}
			}
			System.out.println("spammers in initial phase"+a);
			System.out.println("spammers with good network"+b);
			System.out.println("spammers with high activity"+c);
			System.out.println("spammers with high activity and netwrok"+d);
			System.out.println("spam accnt in future"+ei);
			System.out.println("legitimate user"+g);
			System.out.println("more active user profile"+h);
			System.out.println("done");
			//
		
		}
		catch (Exception e) {
			System.out.println("Error in CsvFileReader !!!");
			e.printStackTrace();
			
	    } 
		finally {
				try {
		                fileReader.close();
				} catch (IOException e) {
			            System.out.println("error in closing file");
			                e.printStackTrace();
			    }
			
	   }
	}

}
