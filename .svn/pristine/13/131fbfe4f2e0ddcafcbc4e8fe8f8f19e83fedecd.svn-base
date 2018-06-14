package com.hysm.db.mongo;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.ListCollectionsIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;









import java.util.ArrayList;
import java.util.List;
import java.util.Set;








import org.bson.Document;
import org.bson.conversions.Bson;


public class MongoUtil {
	private static String dbname=MongoConn.get_database();
    private static MongoClient mclient;
    private  MongoDatabase mdb;
    public String threadFlag = null;
    
    private MongoUtil() {
    	
        if (mclient == null){
        	
        	mclient = MongoConn.getConn();
        	
        	 mdb=mclient.getDatabase(dbname);
        }else{
           if(mdb==null){
        	   
           	 mdb=mclient.getDatabase(dbname);
          
           }
        } 
        
    }
    
    private static MongoUtil  single=null;
	//为了防止对象的多次无谓生成
	public static MongoUtil getThreadInstance()
	{
		
		  if (single == null) {    
	            synchronized (MongoUtil.class) {    
	               if (single == null) {    
	            	   single = new MongoUtil();   
	               }    
	            }    
	        }else{
	    	  
	    	 if( single.mclient==null){
	    		 single.mclient = MongoConn.getConn();
	         	
	    		 single.mdb=single.mclient.getDatabase(dbname);
	    	 }
	    	 
	      }
		
		return single;
		
	}
    public   synchronized void closeConnection(){  
        
        if(mclient != null){  
              
        	mclient.close();  
        	 
        } 
        
        if(mclient!=null){
        	mclient=null;
    	}
    } 
    
  
	public MongoUtil clear()
	{
		//清除重复数据
		if (mclient == null){
			
			mclient = MongoConn.getConn();
			
        	mdb=mclient.getDatabase(dbname);
        	
        }
		 
		return this;
	}
	
	
	public List<String> queryallTables(){
			MongoIterable<String> c=	 mdb.listCollectionNames();
			
			if(c!=null&&c.iterator()!=null){
				List<String> tables=new ArrayList<String>();
				MongoCursor<String> cc=c.iterator();
				while(cc.hasNext()){
					tables.add(cc.next());
					
				}
				
				
				return tables;
			}
			
			return null;
	}
	public List<Document> queryallTables2(){
		ListCollectionsIterable<Document> c=	 mdb.listCollections();
		
		if(c!=null&&c.iterator()!=null){
			List<Document> tables=new ArrayList<Document>();
			MongoCursor<Document> cc=c.iterator();
			while(cc.hasNext()){
				Document t=cc.next();
				String tn=t.getString("name");
				if(!tn.equals("system.indexes")){
					List<Document> index=this.queryColumns(tn);
					
					t.put("cols", index);
					tables.add(t);
				}
				
				
			}
			
			
			return tables;
		}
		
		return null;
}
	
	public List<Document> queryColumns(String table){
		Document col=mdb.getCollection(table).find().first();
		if(col!=null){
			  Set<String> keys=col.keySet();
			  List<Document> coldocs=new ArrayList<Document>();
			  for(String onecol:keys){
				  Document onecoldoc=new Document();
				  
				  onecoldoc.put("name", onecol);
				  onecoldoc.put("checkon", -1);
				  coldocs.add(onecoldoc);
			  }
			  
			  return coldocs;
		}
		return null;
	}
	
	public static void main(String[] args) {
		MongoUtil mu=MongoUtil.getThreadInstance();
		System.out.println(mu.queryallTables2());
	}
	
	
    public void deleteMany(String collection, Document conditionDoc) {
        MongoCollection<BasicDBObject> mcoll = mdb.getCollection(collection, BasicDBObject.class);
        mcoll.deleteMany(conditionDoc);
    }

    public void insertOne(String collection, BasicDBObject document) {
    	
    	
        MongoCollection<BasicDBObject> mcoll = mdb.getCollection(collection, BasicDBObject.class);
        
        
        mcoll.insertOne(document);
    }

    public void insertOne(String collection, Document document) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection, Document.class);
        mcoll.insertOne(document);
    }

    public void insertMany(String collection, List<Document> documents) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        mcoll.insertMany(documents);
        
    }
    
    
    public void insertMany2Index(String collection, List<Document> documents) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        long count=mcoll.count();
        count++;
        for(Document d:documents){
        	d.put("_id", count);
        	count++;
        	
        }
        
        mcoll.insertMany(documents);
        
    }
    
  

    public int updateOne(String collection, Bson condition, Bson setfields) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        UpdateResult  res= mcoll.updateOne(condition, setfields);
         
       // Filters.and(Filters.eq("phone", ""),Filters.eq("phone", ""));
        
//        mcoll.updateMany(
//                eq("stars", 2),
//                combine(set("stars", 0), currentUpdatesDate("lastModified")));
        //    updateOne(Filters.eq("i", 1), Updates.set("i", 110));  

    	return  (int)res.getModifiedCount();
    }
     

    public int replaceOne(String collection, Bson condition, Document document) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        UpdateResult  res= mcoll.replaceOne(condition, document);
        
        return  (int)res.getModifiedCount();
    }
    
    

    public int deleteMany(String collection, Bson condition) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
       DeleteResult  res= mcoll.deleteMany(condition);
        return  (int)res.getDeletedCount();
    }

    
    
    public List<Document> find(String collection, Bson condition) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        FindIterable<Document> iterable = null;
        if (condition == null) {
            iterable = mcoll.find();
        } else {
            iterable = mcoll.find(condition);
        }
        if (iterable != null) {
        	
        	MongoCursor<Document>  docs=iterable.iterator();
            
            return this.filterDocument(docs);
            
            
        } else {
            return null;
        }
        /*
         * 调用例子
         *
         * MongoCursor<Document> cursor = find(.............);
         * while (cursor.hasNext()) {
         *      Document user = cursor.next();
         *      System.out.println(user.toString());
         * }
         */
    }
  

	public List<Document> find(String collection, Bson condition,Bson columns) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        FindIterable<Document> iterable = null;
        if (condition == null) {
            iterable = mcoll.find();
        } else {
            iterable = mcoll.find(condition);
        }
        
        if(columns!=null){
        	iterable.projection(columns);
        }
        
        if (iterable != null) {
            return this.filterDocument(iterable.iterator());
        } else {
            return null;
        }
        /*
         * 调用例子
         *
         * MongoCursor<Document> cursor = find(.............);
         * while (cursor.hasNext()) {
         *      Document user = cursor.next();
         *      System.out.println(user.toString());
         * }
         */
    }
	public JSONArray findJA(String collection, Bson condition,Bson columns) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        FindIterable<Document> iterable = null;
        if (condition == null) {
            iterable = mcoll.find();
        } else {
            iterable = mcoll.find(condition);
        }
        
        if(columns!=null){
        	iterable.projection(columns);
        }
        
        if (iterable != null) {
            return this.filterDocumentToJSON(iterable.iterator());
        } else {
            return null;
        }
        /*
         * 调用例子
         *
         * MongoCursor<Document> cursor = find(.............);
         * while (cursor.hasNext()) {
         *      Document user = cursor.next();
         *      System.out.println(user.toString());
         * }
         */
    }
   
	public List<Document> findList(String collection, Bson condition) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        FindIterable<Document> iterable = null;
        if (condition == null) {
            iterable = mcoll.find();
        } else {
            iterable = mcoll.find(condition);
        }
        if (iterable != null) {
        	 return this.filterDocument(iterable.iterator());
        	
        }
        return null;
        /*
         * 调用例子
         *
         * MongoCursor<Document> cursor = find(.............);
         * while (cursor.hasNext()) {
         *      Document user = cursor.next();
         *      System.out.println(user.toString());
         * }
         */
    }
    
    
    
    public Document findOne(String collection, Bson condition) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        FindIterable<Document> iterable = null;
        if (condition == null) {
            iterable = mcoll.find();
        } else {
            iterable = mcoll.find(condition);
        }
        if (iterable != null&&iterable.iterator().hasNext()) {
        	
        	 return this.filterOneDocument(iterable.iterator());
        	
            
            
        } else {
            return null;
        }
        /*
         * 调用例子
         *
         * MongoCursor<Document> cursor = find(.............);
         * while (cursor.hasNext()) {
         *      Document user = cursor.next();
         *      System.out.println(user.toString());
         * }
         */
    }
    
    public Document findOne(String collection, Bson condition,Bson columns){
    	
    	 MongoCollection<Document> mcoll = mdb.getCollection(collection);
         FindIterable<Document> iterable = null;
         if (condition == null) {
             iterable = mcoll.find();
         } else {
             iterable = mcoll.find(condition);
         }
         
         if(columns!=null){
         	iterable.projection(columns);
         }
         
         if (iterable != null&&iterable.iterator().hasNext()) {
         	
        	 return this.filterOneDocument(iterable.iterator());
             
             
         } else {
             return null;
         }
    }

    public void find(String collection, Bson condition, Block<Document> block) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        if (condition == null) {
            mcoll.find().forEach(block);
        } else {
            mcoll.find(condition).forEach(block);
        }
        /**
         * 调用例子
         * find("collection",new Document("customerid", customerid).append("useracc", user),
         *                    (Block<Document>) document-> {
         *                        System.out.println(document.toJson());
         *                        //System.out.println("aaa");
         *                    });
         */
    }

    /**
     * 获取记录条数
     *
     * @param collection
     * @param condition
     * @return
     */
    public long count(String collection, Bson condition) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        if (condition == null) {
            return mcoll.count();
        } else {
            return mcoll.count(condition);
        }
    }

    public List<Document> findLimit(String collection, Bson condition, int skip, int limit) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        FindIterable<Document> iterable;
        if (condition == null) {
            iterable = mcoll.find().skip(skip).limit(limit);
        } else {
            iterable = mcoll.find(condition).skip(skip).limit(limit);
        }
        if (iterable != null) {
        	 return this.filterDocument(iterable.iterator());
        } else {
            return null;
        }
    }

    public void findLimit(String collection, Bson condition, int skip, int limit, Block<Document> block) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        if (condition == null) {
            mcoll.find().skip(skip).limit(limit).forEach(block);
        } else {
            mcoll.find(condition).skip(skip).limit(limit).forEach(block);
        }

    }

    public List<Document> findLimitSort(String collection, Bson condition, int skip, int limit, Bson sort) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        FindIterable<Document> iterable = null;
        if (condition == null) {
            iterable = mcoll.find().skip(skip).limit(limit).sort(sort);
        } else {
            iterable = mcoll.find(condition).skip(skip).limit(limit).sort(sort);
        }
        if (iterable != null) {
        	 return this.filterDocument(iterable.iterator());
        } else {
            return null;
        }
    }

    public void findLimitSort(String collection, Bson condition, int skip, int limit, Bson sort, Block<Document> block) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        if (condition == null) {
            mcoll.find().skip(skip).limit(limit).sort(sort).forEach(block);
        } else {
            mcoll.find(condition).skip(skip).limit(limit).sort(sort).forEach(block);
        }
    }

    public List<Document> findSort(String collection, Bson condition, Bson sort) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        FindIterable<Document> iterable = null;
        if (condition == null) {
            iterable = mcoll.find().sort(sort);
        } else {
            iterable = mcoll.find(condition).sort(sort);
        }
        if (iterable != null) {
        	 return this.filterDocument(iterable.iterator());
        } else {
            return null;
        }
    }

    public void findSort(String collection, Bson condition, Bson sort, Block<Document> block) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        if (condition == null) {
            mcoll.find().sort(sort).forEach(block);
        } else {
            mcoll.find(condition).sort(sort).forEach(block);
        }
    }
    
    
	public int updateMany(String tABLE_1, Bson eq, Document set) {
		 MongoCollection<Document> mcoll = mdb.getCollection(tABLE_1);
		 UpdateResult res= mcoll.updateMany(eq, set);
		return  (int)res.getModifiedCount();
		
	}
    

	
	
	 public List<Document> groupTodocs(String collection,String keyname,List<BasicDBObject> arr){
	    	List<Bson> bsons=new ArrayList<Bson>();
	    		
	    		
			/*//BasicDBObject kwbson=new BasicDBObject("KW", new BasicDBObject("$eq",kw));
			
			if(arr!=null){
				arr.add(kwbson);
			}else{
				arr=new ArrayList<BasicDBObject>();
				arr.add(kwbson);
				
			}*/
			if(arr!=null&&arr.size()>0){
				BasicDBObject cond = new BasicDBObject();
				cond.put("$and", arr); 
				
				Document b1=new Document();
		    	b1.put("$match", cond);
		    	
		    	bsons.add(b1);
			}
			
	        
			Document b=new Document();
			DBObject groupFields = new BasicDBObject( "_id", new BasicDBObject().append("_id", "$"+keyname).append("sortname", "$sortname"));   
		    groupFields.put("count", new BasicDBObject( "$sum", 1));  
		    
		  //sort  
	        //DBObject sort = new BasicDBObject("$sort", new BasicDBObject("_id", 1));  
	        //limit  
	       // DBObject limit = new BasicDBObject("$limit", 5);  
		    
			b.put("$group", groupFields);
			bsons.add(b);
	    	AggregateIterable<Document>	ss=mdb.getCollection(collection).aggregate(bsons);
	    	
	    	MongoCursor<Document> it=ss.iterator();
	    	
	    
	    	return filterDocument(it);
	    }
	 
	 public List<String> groupByKkey(String collection,String keyname,List<BasicDBObject> arr){
	    	List<Bson> bsons=new ArrayList<Bson>();
	    		
	    		
			/*//BasicDBObject kwbson=new BasicDBObject("KW", new BasicDBObject("$eq",kw));
			
			if(arr!=null){
				arr.add(kwbson);
			}else{
				arr=new ArrayList<BasicDBObject>();
				arr.add(kwbson);
				
			}*/
			if(arr!=null&&arr.size()>0){
				BasicDBObject cond = new BasicDBObject();
				cond.put("$and", arr); 
				
				Document b1=new Document();
		    	b1.put("$match", cond);
		    	
		    	bsons.add(b1);
			}
			
	        
			Document b=new Document();
			DBObject groupFields = new BasicDBObject( "_id", "$"+keyname);   
		    //groupFields.put("count", new BasicDBObject( "$sum", 1));   
		  //sort  
	        //DBObject sort = new BasicDBObject("$sort", new BasicDBObject("_id", 1));  
	        //limit  
	       // DBObject limit = new BasicDBObject("$limit", 5);  
		    
			b.put("$group", groupFields);
			bsons.add(b);
	    	AggregateIterable<Document>	ss=mdb.getCollection(collection).aggregate(bsons);
	    	
	    	 List<String> all=new ArrayList<String>();
	    	MongoCursor<Document> it=ss.iterator();
	    	while(it.hasNext()){
	    		Document doc=it.next();
	    		all.add(doc.getString("userid"));
	    	}
	    
	    	return all;
	    }
	 
	 
	 /**
	  * 保存/替换
	  * @param table_order
	  * @param order
	  */
	public int saveOne(String table_order, Document order) {
		
		 MongoCollection<Document> mcoll = mdb.getCollection(table_order);
		 
		 int count=(int)mcoll.count(Filters.eq("_id", order.get("_id")));
		 if(count==0){
			 mcoll.insertOne(order);
			 return 1;
		 }else{
			return  (int)mcoll.replaceOne(Filters.eq("_id", order.get("_id")), order).getModifiedCount();
		 }
	}
	public  List<Document> groupByid(String table_table, List<String> keys,
			List<BasicDBObject> arr) {
    	List<Bson> bsons=new ArrayList<Bson>();
		
		
		/*//BasicDBObject kwbson=new BasicDBObject("KW", new BasicDBObject("$eq",kw));
		
		if(arr!=null){
			arr.add(kwbson);
		}else{
			arr=new ArrayList<BasicDBObject>();
			arr.add(kwbson);
			
		}*/
		if(arr!=null&&arr.size()>0){
			BasicDBObject cond = new BasicDBObject();
			cond.put("$and", arr); 
			
			Document b1=new Document();
	    	b1.put("$match", cond);
	    	
	    	bsons.add(b1);
		}
		
        
		Document b=new Document();
		BasicDBObject keyarr=new BasicDBObject();
		for(String onestr:keys){
			keyarr.append(onestr, "$"+onestr);
		}
		DBObject groupFields = new BasicDBObject( "_id", keyarr);   
	   
		
		groupFields.put("count", new BasicDBObject( "$sum", 1));  
	    
	  //sort  
        //DBObject sort = new BasicDBObject("$sort", new BasicDBObject("_id", 1));  
        //limit  
       // DBObject limit = new BasicDBObject("$limit", 5);  
	    
		b.put("$group", groupFields);
		bsons.add(b);
    	AggregateIterable<Document>	ss=mdb.getCollection(table_table).aggregate(bsons);
    	
    	MongoCursor<Document> it=ss.iterator();
    	
    
    	return filterDocument(it);
    }
	
	
	 public List<Document> groupByKkey2(String collection,String keyname,List<BasicDBObject> arr){
	    	List<Bson> bsons=new ArrayList<Bson>();
	    		
	    		
			/*//BasicDBObject kwbson=new BasicDBObject("KW", new BasicDBObject("$eq",kw));
			
			if(arr!=null){
				arr.add(kwbson);
			}else{
				arr=new ArrayList<BasicDBObject>();
				arr.add(kwbson);
				
			}*/
			if(arr!=null&&arr.size()>0){
				BasicDBObject cond = new BasicDBObject();
				cond.put("$and", arr); 
				
				Document b1=new Document();
		    	b1.put("$match", cond);
		    	
		    	bsons.add(b1);
			}
			
	        
			Document b=new Document();
			DBObject groupFields = new BasicDBObject( "_id", "$"+keyname);  
			groupFields.put("count", new BasicDBObject( "$sum", 1)); 
		    //groupFields.put("count", new BasicDBObject( "$sum", 1));   
		  //sort  
	        //DBObject sort = new BasicDBObject("$sort", new BasicDBObject("_id", 1));  
	        //limit  
	       // DBObject limit = new BasicDBObject("$limit", 5);  
		    
			b.put("$group", groupFields);
			bsons.add(b);
	    	AggregateIterable<Document>	ss=mdb.getCollection(collection).aggregate(bsons);
	    	
	    	MongoCursor<Document> it=ss.iterator();
	    	
	    
	    	return filterDocument(it);
	    }
	 
	 
	public  Document groupSum(String table, String key,String column,
			List<BasicDBObject> arr) {
		List<Bson> bsons=new ArrayList<Bson>();
		
		
		if(arr!=null&&arr.size()>0){
			BasicDBObject cond = new BasicDBObject();
			cond.put("$and", arr); 
			
			Document b1=new Document();
	    	b1.put("$match", cond);
	    	
	    	bsons.add(b1);
		}
		
        
		Document b=new Document();
		DBObject groupFields = new BasicDBObject( "_id", "$"+key);  
		groupFields.put("sum", new BasicDBObject( "$sum", "$"+column)); 
	  
	    
		b.put("$group", groupFields);
		bsons.add(b);
    	AggregateIterable<Document>	ss=mdb.getCollection(table).aggregate(bsons);
    	
    	MongoCursor<Document> it=ss.iterator();
    	
    	return filterOneDocument(it);
    	
	}

	
	/**
	 * 获取单个doc
	 * @param it
	 * @return
	 */
	private Document filterOneDocument(MongoCursor<Document> it) {
		if(it!=null){
			return filterColumns(it.next());
		}
		return null;
	}
	 
	/**
	 * 获取多个doc
	 * @param c
	 * @return
	 */
	private List<Document> filterDocument(MongoCursor<Document> c) {
		
			
		
		if(c!=null){
			List<Document> list=new ArrayList<Document>();
			while(c.hasNext()){
				
				list.add(filterColumns(c.next()));
			}
			
			return list;
		}
		
		return null;
	}
	
	/**
	 * 获取多个doc
	 * @param c
	 * @return
	 */
	private JSONArray filterDocumentToJSON(MongoCursor<Document> c) {
		
		if(c!=null){
			JSONArray list=new JSONArray();
			while(c.hasNext()){
				
				list.add(new JSONObject(filterColumns(c.next())));
			}
			
			return list;
		}
		
		return null;
	}
	
	/**
	 * 敏感字段的拦截
	 * @param next
	 * @return
	 */
	 private Document filterColumns(Document doc) {
		
		 
		 return DBColumnsFilter.mongofilter(doc);
	 
	 }
}
